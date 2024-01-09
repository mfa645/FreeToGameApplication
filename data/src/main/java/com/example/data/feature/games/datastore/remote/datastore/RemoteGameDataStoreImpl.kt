package com.example.data.feature.games.datastore.remote.datastore

import com.example.data.feature.games.datastore.interfaces.RemoteGamesDataStore
import com.example.data.feature.games.datastore.mappers.toDomain
import com.example.data.feature.games.datastore.remote.GamesService
import com.example.model.feature.games.Game

internal class RemoteGameDataStoreImpl(
    private val gamesService: GamesService
) : RemoteGamesDataStore {
    override suspend fun getAllGames(): List<Game> {
        val result = this@RemoteGameDataStoreImpl.gamesService.getAllGames()

        if (result.isSuccessful)
            return (result.body()?.map { game ->
                game.toDomain()
            } ?: emptyList())

        return emptyList()
    }
}