package com.example.domain.feature.games

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.feature.games.paging.FilteredGamesPaging
import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

class GetFilteredGamesUseCase (
    private val gamesRepository : GamesRepository
) {
    suspend operator fun invoke(titleFilter:String,genreFilter: String, platformFilter: String, isToPlayGames: Boolean): List<Game> {
        return gamesRepository.getFilteredGames(titleFilter,genreFilter,platformFilter,isToPlayGames)
    }

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