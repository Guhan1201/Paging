package com.example.pagging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pagging.adapter.PostDataSource

class MainViewModel(private val postDataSource: PostDataSource) : ViewModel() {


    val listData = Pager(PagingConfig(pageSize = 6)) {
        postDataSource
    }.liveData.cachedIn(viewModelScope)

}

class MainViewModelFactory(private val postDataSource: PostDataSource) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(postDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}