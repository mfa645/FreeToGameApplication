package com.example.domain.di

import android.content.Context
import com.example.data.di.DataModuleInjector
import com.example.domain.feature.games.AddGamesUseCase
import com.example.domain.feature.games.AllGamesUseCase
import com.example.domain.feature.games.DeleteGameUseCase
import com.example.domain.feature.games.EditGameUseCase
import com.example.domain.feature.games.GetFilteredGamesUseCase
import com.example.domain.feature.games.GetGameUseCase
import com.example.model.di.KoinModuleLoader
import org.koin.core.module.Module
import org.koin.core.scope.get
import org.koin.dsl.module


object DomainModuleInjection: KoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> =
        DataModuleInjector.getKoinModules(context)
            .union(
                listOf(
                    module {
                        factory { AllGamesUseCase(get(),get()) }
                        factory { AddGamesUseCase(get()) }
                        factory { DeleteGameUseCase(get()) }
                        factory { EditGameUseCase(get()) }
                        factory { GetFilteredGamesUseCase(get()) }
                        factory { GetGameUseCase(get()) }

                    }
                )
            ).toList()
}