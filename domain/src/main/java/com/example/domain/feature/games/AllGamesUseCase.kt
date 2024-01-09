package com.example.domain.feature.games


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.feature.games.paging.GamesPaging
import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game


class AllGamesUseCase(
    private val gamesRepository : GamesRepository,
    private val pagingSource: GamesPaging
) {
    operator fun invoke(limit: Int) = Pager(
        config = PagingConfig(
            pageSize = limit,
            prefetchDistance = limit / 2
        ),
        pagingSourceFactory = {
            pagingSource
        }
    ).flow

    suspend operator fun invoke(): List<Game> {
        return gamesRepository.getAllGames()
    }
}