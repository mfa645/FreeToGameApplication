package com.example.freetogameapplication.di

import android.content.Context
import com.example.domain.di.DomainModuleInjection
import com.example.model.di.KoinModuleLoader
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel


object AppModuleInjector : KoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> =
        DomainModuleInjection.getKoinModules(context)
            .union(
                listOf(
                    module {
                        viewModel { GamesViewModel(get(), get(), get(), get(),get()) }
                    }
                )
            ).toList()
}