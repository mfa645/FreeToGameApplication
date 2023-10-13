package com.example.data.feature.games.datastore.remote

import com.example.data.feature.games.datastore.remote.model.RemoteGame
import com.example.data.feature.services.remote.FreeToGameService
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesService : FreeToGameService {
    @GET("api/games")
    suspend fun getAllGames(): Response<List<RemoteGame>>

    @GET("api/games")
    suspend fun getFilteredGames(
        @Query("platform") platform: PlatformFilter? = PlatformFilter.PCAndWebBrowser,
        @Query("tag") tag: GenreFilter?,
    ): Response<List<RemoteGame>>

    @GET("api/game")
    suspend fun getGameById(
        @Query("id") id: Int,
    ): Response<RemoteGame>

}