package com.example.esport.di

import com.example.esport.di.NetworkModule
import com.example.esport.ui.home.ESportsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(eSportsViewModel: ESportsViewModel)
}