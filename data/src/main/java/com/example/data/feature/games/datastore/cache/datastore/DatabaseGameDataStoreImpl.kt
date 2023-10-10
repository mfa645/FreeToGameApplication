package com.example.data.feature.games.datastore.cache.datastore

import android.util.Log
import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.data.feature.games.datastore.interfaces.GamesDataStore
import com.example.data.feature.games.datastore.mappers.toDomain
import com.example.data.feature.services.database.AppDatabase
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import com.example.model.feature.games.enums.SortFilter

internal class DatabaseGameDataStoreImpl(
    private val appDatabase: AppDatabase
) : GamesDataStore {
    override suspend fun getAllGames(page: Int, limit: Int): List<Game> {
        val result = appDatabase.gamesDao().getAllGames(page = page, limit = limit)
        return if (result.isEmpty()) emptyList()
        else result.map { databaseGame -> databaseGame.toDomain() }
    }

    override suspend fun getAllGames(): List<Game> {
        val result = appDatabase.gamesDao().getAllGames()
        return if (result.isEmpty()) emptyList()
        else result.map { databaseGame ->
            databaseGame.toDomain()
        }
    }

    override suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: GenreFilter,
        filterByPlatform: PlatformFilter,
        filterBySort: SortFilter
    ): List<Game> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameById(gameId: Int): Game {
        val result = this@DatabaseGameDataStoreImpl.appDatabase.gamesDao().getGameById(gameId)
        return result.toDomain()
    }

    override fun editGame(game: DatabaseGame) {
        this@DatabaseGameDataStoreImpl.appDatabase.gamesDao().editGame(game)
    }

    override fun addGames(vararg games: DatabaseGame) {
        this@DatabaseGameDataStoreImpl.appDatabase.gamesDao().addGames(*games)
    }

    override fun deleteGame(game: DatabaseGame) {
        this@DatabaseGameDataStoreImpl.appDatabase.gamesDao().deleteGame(game)
    }
}