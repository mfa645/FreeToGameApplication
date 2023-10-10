package com.example.freetogameapplication

import android.app.Application
import com.example.freetogameapplication.di.AppModuleInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class FreeToGameApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@FreeToGameApplication)
            modules(
                AppModuleInjector.getKoinModules(
                    this@FreeToGameApplication
                )
            )
        }
    }
}