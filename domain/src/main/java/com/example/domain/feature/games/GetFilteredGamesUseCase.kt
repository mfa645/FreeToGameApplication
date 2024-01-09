package com.example.domain.feature.games

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.feature.games.paging.FilteredGamesPaging
import com.example.data.feature.games.repository.GamesRepository

class GetFilteredGamesUseCase (
    private val gamesRepository : GamesRepository
) {
    operator fun invoke(limit: Int, titleFilter:String,genreFilter: String, platformFilter: String, isToPlayGames: Boolean) = Pager(
        config = PagingConfig(
            pageSize = limit,
            prefetchDistance = limit / 2
        ),
        pagingSourceFactory = {
            FilteredGamesPaging(gamesRepository, titleFilter, genreFilter, platformFilter, isToPlayGames)
        }
    ).flow
}