package com.example.productlisting

import android.app.Application
import com.example.productlisting.di.appModule
import com.example.productlisting.di.repoModule
import com.example.productlisting.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}