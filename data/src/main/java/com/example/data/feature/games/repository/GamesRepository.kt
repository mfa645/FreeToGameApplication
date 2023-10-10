package com.example.data.feature.games.repository

import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import com.example.model.feature.games.enums.SortFilter

interface GamesRepository {
    suspend fun getAllGames(page: Int): List<Game>
    suspend fun getAllGames(isLocal : Boolean): List<Game>


    fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: GenreFilter,
        filterByPlatform: PlatformFilter,
        filterBySort: SortFilter
    ): List<Game>

    suspend fun getGameById(gameId: Int): Game

    fun addToPlayGames(vararg game: Game)
    fun editGame(game: Game)

    fun deleteGame(game:Game)
}