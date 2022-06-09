package com.testTask.chibbistest

import android.app.Application
import com.testTask.chibbistest.di.networkModule
import com.testTask.chibbistest.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class ChibbisTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ChibbisTestApplication)
            modules(listOf(networkModule, repositoryModule))
        }
    }
}