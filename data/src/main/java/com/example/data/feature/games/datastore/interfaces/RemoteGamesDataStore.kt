package com.example.data.feature.games.datastore.interfaces

import com.example.model.feature.games.Game

interface RemoteGamesDataStore {
    suspend fun getAllGames(): List<Game>
}