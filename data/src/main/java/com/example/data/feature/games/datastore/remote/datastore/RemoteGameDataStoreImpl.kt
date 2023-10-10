package com.example.data.feature.games.datastore.remote.datastore

import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.data.feature.games.datastore.interfaces.GamesDataStore
import com.example.data.feature.games.datastore.mappers.toDomain
import com.example.data.feature.games.datastore.remote.GamesService
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import com.example.model.feature.games.enums.SortFilter

internal class RemoteGameDataStoreImpl(
    private val gamesService: GamesService
) : GamesDataStore {
    override suspend fun getAllGames(page: Int, limit: Int): List<Game> {
        TODO("Not supported")
    }

    override suspend fun getAllGames(): List<Game> {
        val result = this@RemoteGameDataStoreImpl.gamesService.getAllGames()

        if (result.isSuccessful)
            return (result.body()?.map { game ->
                game.toDomain()
            } ?: emptyList())

        return emptyList()
    }

    override suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: GenreFilter,
        filterByPlatform: PlatformFilter,
        filterBySort: SortFilter
    ): List<Game> {
        val result = this@RemoteGameDataStoreImpl.gamesService.getFilteredGames(
            platform = filterByPlatform,
            tag = filterByGenre,
            sortBy = filterBySort
        )
        if (result.isSuccessful)
            return (result.body()?.map { game ->
                game.toDomain()
            } ?: emptyList())
        return emptyList()
    }

    override suspend fun getGameById(gameId: Int): Game {
        val result = this@RemoteGameDataStoreImpl.gamesService.getGameById(gameId)
        if (result.isSuccessful)
            return result.body()?.toDomain() ?: throw Exception("The game cannot be found")
        else
            throw Exception("The game cannot be found")
    }

    override fun editGame(game: DatabaseGame) {
        TODO("Not supported")
    }

    override fun addGames(vararg game: DatabaseGame) {
        TODO("Not supported")
    }

    override fun deleteGame(game: DatabaseGame) {
        TODO("Not supported")
    }

}