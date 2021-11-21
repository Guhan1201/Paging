package com.example.pagging.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagging.adapter.MainListAdapter
import com.example.pagging.adapter.ReposLoadStateAdapter
import com.example.pagging.databinding.ActivityMainBinding
import com.example.pagging.di.DaggerAppComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainListAdapter: MainListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DaggerAppComponent.builder().build().inject(this)
        setupViewModel()
        setupList()
        setupView()
    }

    private fun setupView() {
        viewModel.listData.observe(this@MainActivity) {
            lifecycleScope.launch {
                mainListAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        mainListAdapter = MainListAdapter()
        mainListAdapter.addLoadStateListener {
//            if (it.refresh == LoadState.Loading) {
//                binding.progressBar.visibility = View.VISIBLE
//                binding.recyclerView.visibility = View.GONE
//            } else {
//                binding.progressBar.visibility = View.GONE
//                binding.recyclerView.visibility = View.VISIBLE
//            }
            // Only show the list if refresh succeeds
            binding.recyclerView.isVisible = it.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            binding.progressBar.isVisible = it.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            binding.retry.isVisible = it.source.refresh is LoadState.Error
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                mainListAdapter.withLoadStateFooter(ReposLoadStateAdapter { mainListAdapter.retry() })
        }
        binding.retry.setOnClickListener {
            mainListAdapter.retry()
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[MainViewModel::class.java]
    }
}