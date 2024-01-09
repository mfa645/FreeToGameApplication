package com.example.data.feature.games.repository

import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

interface GamesRepository {
    suspend fun getAllGames(page: Int, limit: Int): List<Game>
    suspend fun getAllGames(isLocal : Boolean): List<Game>

    suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames:Boolean
    ): List<Game>
    suspend fun getFilteredGames(
        limit: Int,
        page:Int,
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames:Boolean
    ): List<Game>

    suspend fun getGameById(gameId: Int): Game

    fun addToPlayGames(vararg game: Game)
    fun editGame(game: Game)

    fun deleteGame(game:Game)
}