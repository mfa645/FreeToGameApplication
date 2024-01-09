package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.feature.games.datastore.cache.datastore.DatabaseGameDataStoreImpl
import com.example.data.feature.games.datastore.interfaces.LocalGamesDataStore
import com.example.data.feature.games.datastore.interfaces.RemoteGamesDataStore
import com.example.data.feature.games.datastore.remote.GamesService
import com.example.data.feature.games.datastore.remote.datastore.RemoteGameDataStoreImpl
import com.example.data.feature.games.factory.GameFactory
import com.example.data.feature.games.paging.GamesPaging
import com.example.data.feature.games.repository.GamesRepository
import com.example.data.feature.games.repository.GamesRepositoryImpl
import com.example.data.feature.services.database.AppDatabase
import com.example.data.feature.services.remote.FreeToGameService
import com.example.model.di.KoinModuleLoader
import org.koin.android.ext.koin.androidContext

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModuleInjector: KoinModuleLoader {
    override fun getKoinModules(context: Context): List<Module> = listOf(
        module {
            single <GamesService>(named("gamesService")){
                Retrofit.Builder()
                    .baseUrl(FreeToGameService.BASE_API)
                    .addConverterFactory(GsonConverterFactory.create(FreeToGameService.makeGson()))
                    .client(FreeToGameService.makeOkHttpClient())
                    .build().create(GamesService::class.java)
            }
            single<AppDatabase>(named("appDatabase")){
                Room.databaseBuilder(androidContext()
                    ,AppDatabase::class.java,
                    "games_db"
                ).build()
            }

            factory<LocalGamesDataStore>(named("database")) {
                DatabaseGameDataStoreImpl(get(named("appDatabase")))
            }

            factory<RemoteGamesDataStore>(named("remote")) {
                RemoteGameDataStoreImpl(get(named("gamesService")))
            }

            factory { GameFactory(get(named("database")), get(named("remote"))) }

            factory<GamesRepository>{ GamesRepositoryImpl(get()) }

            factory { GamesPaging(get()) }

        }
    )
}