package com.example.domain.feature.games

import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

class GetFilteredGamesUseCase (
    private val gamesRepository : GamesRepository
) {
    suspend operator fun invoke(titleFilter:String,genreFilter: GenreFilter?, platformFilter: PlatformFilter?, isToPlayGames: Boolean): List<Game> {
        return gamesRepository.getFilteredGames(titleFilter,genreFilter,platformFilter,isToPlayGames)
    }
}