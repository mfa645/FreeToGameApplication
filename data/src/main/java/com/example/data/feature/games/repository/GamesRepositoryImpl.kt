package com.example.data.feature.games.repository

import com.example.data.feature.games.datastore.mappers.toDatabase
import com.example.data.feature.games.factory.GameFactory
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import com.example.model.feature.games.enums.SortFilter

internal class GamesRepositoryImpl(
    private val factory: GameFactory
) : GamesRepository {
    override suspend fun getAllGames(page: Int): List<Game> =
        factory.cache.getAllGames()

    override suspend fun getAllGames(isLocal: Boolean): List<Game> =
        if(isLocal)
            factory.cache.getAllGames()
        else
            factory.remote.getAllGames()

    override fun getFilteredGames(
        filterByTitle: String,
        filterByGenre: GenreFilter,
        filterByPlatform: PlatformFilter,
        filterBySort: SortFilter,

        ): List<Game> {
        TODO("Not yet implemented")
    }

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



