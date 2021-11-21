package com.example.pagging.di

import androidx.lifecycle.ViewModelProvider
import com.example.pagging.network.APIService
import com.example.pagging.repository.Repository
import com.example.pagging.repository.RepositoryImpl
import com.example.pagging.ui.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun getRetrofit(): APIService {
        return APIService.getApiService()
    }

    @Provides
    fun getViewModelProvider(repository: Repository): ViewModelProvider.Factory {
        return MainViewModelFactory(repository)
    }
}