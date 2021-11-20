package com.example.pagging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var mainListAdapter: MainListAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                MainViewModelFactory(APIService.getApiService())
            )[MainViewModel::class.java]
    }
}