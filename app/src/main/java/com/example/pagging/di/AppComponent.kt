package com.example.pagging.di

import com.example.pagging.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainActivityModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}