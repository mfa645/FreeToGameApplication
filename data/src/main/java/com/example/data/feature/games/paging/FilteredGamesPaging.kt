package com.example.data.feature.games.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

open class FilteredGamesPaging(
    private val repository: GamesRepository,
    private val filterByTitle: String,
    private val filterByGenre: String,
    private val filterByPlatform: String,
    private val isToPlayGames: Boolean,
): PagingSource<Int, Game>() {
    //En vistas clásicas se utiliza PagingDataAdapter
    //================================================

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Aquí sí hay que tocar un poquito...
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> =
        try {
            withContext(Dispatchers.IO) {
                val page = params.key ?: 0
                val limit = params.loadSize
                Timber.tag("Paging").i("Page: $page")
                // Puede ser un flow, o una suspend que se traiga cosas de cualquier sitio,
                // es un repositorio, por tanto...
                val response = repository.getFilteredGames(
                    limit = limit,
                    page= page,
                    filterByTitle =  filterByTitle,
                    filterByGenre =  filterByGenre,
                    filterByPlatform =  filterByPlatform,
                    isToPlayGames =  isToPlayGames
                )

                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 0) null else page.minus(1),
                    nextKey = if (response.isEmpty()) null else page.plus(1),
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}