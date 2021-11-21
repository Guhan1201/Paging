package com.example.pagging.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagging.model.Data
import com.example.pagging.network.APIService
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostDataSource @Inject constructor(private val apiService: APIService) :
    PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<Data>()
            delay(2000)
            val data = response.body()?.myData ?: emptyList()
            responseData.addAll(data)
            return LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey =
                if(data.isEmpty()) null else currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            Log.e("Guhan", e.toString())
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}