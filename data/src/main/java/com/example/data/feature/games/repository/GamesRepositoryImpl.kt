package com.example.data.feature.games.repository

import com.example.data.feature.games.datastore.mappers.toDatabase
import com.example.data.feature.games.factory.GameFactory
import com.example.model.feature.games.Game

internal class GamesRepositoryImpl(
    private val factory: GameFactory
) : GamesRepository {
    override suspend fun getAllGames(page: Int, limit: Int): List<Game> =
        factory.cache.getAllGames(page, limit)

    override suspend fun getAllGames(): List<Game> =
        factory.remote.getAllGames()

    override suspend fun getFilteredGames(
        limit: Int,
        page: Int,
        filterByTitle: String,
        filterByGenre: String,
        filterByPlatform: String,
        isToPlayGames: Boolean
    ): List<Game> =
        factory.cache.getFilteredGames(
            limit,
            page,
            filterByTitle,
            filterByGenre,
            filterByPlatform,
            isToPlayGames
        )

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



