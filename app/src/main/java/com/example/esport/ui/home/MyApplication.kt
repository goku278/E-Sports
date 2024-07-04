package com.example.esport.ui.home

import android.app.Application
import com.example.esport.di.ApplicationComponent
import com.example.esport.di.DaggerApplicationComponent
import com.example.esport.di.NetworkModule

class MyApplication : Application() {
    private lateinit var retroComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        retroComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(this))
            .build()

    }

    fun getRetroComponent(): ApplicationComponent {
        return retroComponent
    }
    // DaggerApplicationComponent
}