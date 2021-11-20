package com.example.pagging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData

class MainViewModel(private val apiService: APIService) : ViewModel() {


    val listData = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(apiService)
    }.liveData.cachedIn(viewModelScope)

}

class MainViewModelFactory(private val apiService: APIService) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}