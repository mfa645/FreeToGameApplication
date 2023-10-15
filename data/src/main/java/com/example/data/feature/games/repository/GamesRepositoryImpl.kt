package com.example.data.feature.games.repository

import com.example.data.feature.games.datastore.mappers.toDatabase
import com.example.data.feature.games.factory.GameFactory
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

internal class GamesRepositoryImpl(
    private val factory: GameFactory
) : GamesRepository {
    override suspend fun getAllGames(page: Int): List<Game> =
        factory.cache.getAllGames()

    override suspend fun getAllGames(isLocal: Boolean): List<Game> =
            if (isLocal) {
                factory.cache.getAllGames()
            } else
                factory.remote.getAllGames()


    override suspend fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames :Boolean
    ): List<Game> =
        factory.cache.getFilteredGames(filterByTitle, filterByGenre, filterByPlatform,isToPlayGames)

    override suspend fun getGameById(gameId: Int): Game = factory.cache.getGameById(gameId)

    override fun addToPlayGames(vararg games: Game) {
        factory.cache.addGames(
            *games.map
            { game -> game.toDatabase() }.toTypedArray()
        )
    }

    override fun editGame(game: Game) {
        factory.cache.editGame(game.toDatabase())
    }

    override fun deleteGame(game: Game) {
        factory.cache.deleteGame(game.toDatabase())
    }

}



