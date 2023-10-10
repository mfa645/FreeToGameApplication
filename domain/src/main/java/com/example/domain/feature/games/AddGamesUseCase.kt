package com.example.domain.feature.games

import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game

class AddGamesUseCase(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(vararg games: Game) {
        return gamesRepository.addToPlayGames(game = games)
    }
}