package com.example.domain.feature.games

import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game

class EditGameUseCase (
    private val gamesRepository : GamesRepository
) {
    suspend operator fun invoke(game: Game) {
        return gamesRepository.editGame(game)
    }
}