package com.example.pagging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pagging.adapter.PostDataSource
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val postDataSource: PostDataSource) : Repository {
    override fun getPager() = Pager(PagingConfig(pageSize = 6)) {
        postDataSource
    }.liveData
}