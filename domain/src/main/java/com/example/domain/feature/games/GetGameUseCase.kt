package com.example.domain.feature.games

import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game

class GetGameUseCase (
    private val gamesRepository : GamesRepository
) {
    suspend operator fun invoke(gameId: Int):Game{
        return gamesRepository.getGameById(gameId)
    }
}