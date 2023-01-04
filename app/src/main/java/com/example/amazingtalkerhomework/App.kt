package com.example.amazingtalkerhomework

import android.app.Application
import com.example.amazingtalkerhomework.di.KoinModules.Companion.initKoin
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initKoin(this)
        Timber.plant(Timber.DebugTree())
    }
}