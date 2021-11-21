package com.example.pagging.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pagging.model.Data

interface Repository {
    fun getPager(): LiveData<PagingData<Data>>
}