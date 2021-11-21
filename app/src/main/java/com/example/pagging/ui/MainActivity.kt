package com.example.pagging.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagging.adapter.MainListAdapter
import com.example.pagging.R
import com.example.pagging.di.DaggerAppComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var mainListAdapter: MainListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.builder().build().inject(this)
        recyclerView = findViewById(R.id.recyclerView)
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
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainListAdapter
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