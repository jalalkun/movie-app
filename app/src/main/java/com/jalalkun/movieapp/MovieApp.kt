package com.jalalkun.movieapp

import android.app.Application
import com.jalalkun.movieapp.modules.apiModule
import com.jalalkun.movieapp.modules.dbModule
import com.jalalkun.movieapp.modules.networkModule
import com.jalalkun.movieapp.modules.repositoryModule
import com.jalalkun.movieapp.modules.viewModelModule
import com.pluto.Pluto
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Pluto.Installer(this)
            .addPlugin(PlutoLoggerPlugin())
            .addPlugin(PlutoNetworkPlugin())
            .install()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MovieApp)
            modules(
                listOf(
                    networkModule,
                    dbModule,
                    apiModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}