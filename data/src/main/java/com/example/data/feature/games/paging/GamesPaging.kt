package com.example.data.feature.games.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.feature.games.repository.GamesRepository
import com.example.model.feature.games.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class GamesPaging(
    private val repository: GamesRepository
) : PagingSource<Int, Game>() {
    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> =
        try {
            withContext(Dispatchers.IO) {
                val page = params.key ?: 0
                val limit = params.loadSize
                val response = repository.getAllGames(
                    page = page,
                    limit = limit
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