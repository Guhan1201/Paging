package com.example.pagging.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
            if (it.refresh == LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                mainListAdapter.withLoadStateFooter(ReposLoadStateAdapter { mainListAdapter.retry() })
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