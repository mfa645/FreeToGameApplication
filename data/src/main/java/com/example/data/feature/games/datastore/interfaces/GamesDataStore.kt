package com.example.data.feature.games.datastore.interfaces

import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import com.example.model.feature.games.enums.SortFilter

interface GamesDataStore {
    suspend fun getAllGames(page:Int,limit:Int): List<Game>
    suspend fun getAllGames(): List<Game>

    suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: GenreFilter,
        filterByPlatform: PlatformFilter,
        filterBySort: SortFilter
    ): List<Game>

    suspend fun getGameById(gameId: Int): Game
    fun editGame(game: DatabaseGame)

    fun addGames(vararg game: DatabaseGame)

    fun deleteGame(game: DatabaseGame)
}