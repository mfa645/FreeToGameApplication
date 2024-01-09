package com.example.data.feature.games.datastore.interfaces

import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

interface GamesDataStore {
    suspend fun getAllGames(page:Int,limit:Int): List<Game>
    suspend fun getAllGames(): List<Game>
    suspend fun getFilteredGames(
        limit: Int,
        page:Int,
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames: Boolean
    ): List<Game>
    suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames: Boolean
    ): List<Game>

    suspend fun getGameById(gameId: Int): Game
    fun editGame(game: DatabaseGame)

    fun addGames(vararg game: DatabaseGame)

    fun deleteGame(game: DatabaseGame)
}