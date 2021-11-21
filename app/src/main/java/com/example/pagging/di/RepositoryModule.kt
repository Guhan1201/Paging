package com.example.pagging.di

import com.example.pagging.repository.Repository
import com.example.pagging.repository.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun getRepository(repository: RepositoryImpl): Repository
}