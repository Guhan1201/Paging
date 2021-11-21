package com.example.pagging.di

import androidx.lifecycle.ViewModelProvider
import com.example.pagging.ui.MainViewModelFactory
import com.example.pagging.adapter.PostDataSource
import com.example.pagging.network.APIService
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun getRetrofit(): APIService {
        return APIService.getApiService()
    }

    @Provides
    fun getViewModelProvider(postDataSource: PostDataSource): ViewModelProvider.Factory {
        return MainViewModelFactory(postDataSource)
    }
}