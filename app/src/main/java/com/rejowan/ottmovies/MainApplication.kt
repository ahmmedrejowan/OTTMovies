package com.rejowan.ottmovies

import android.app.Application
import com.rejowan.ottmovies.di.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        // initialize Koin for dependency injection
        startKoin {
            androidLogger(Level.ERROR)             // logger
            androidContext(this@MainApplication)
            modules(listOf(movieModule))
        }


    }


}