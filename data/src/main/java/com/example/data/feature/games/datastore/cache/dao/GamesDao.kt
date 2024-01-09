package com.example.data.feature.games.datastore.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

@Dao
interface GamesDao {
    @Query("SELECT * FROM games limit :limit offset (:page * :limit)")
    fun getAllGames(page: Int, limit: Int): List<DatabaseGame>

    @Query("SELECT * FROM games")
    fun getAllGames(): List<DatabaseGame>

    @Query("SELECT * FROM games WHERE id = :gameId LIMIT 1")
    fun getGameById(gameId: Int): DatabaseGame



    @Query("SELECT * FROM games WHERE title like '%'||:filterTitle||'%'AND genre like :genreFilter AND platform like :platformFilter")
    fun getFilteredGames(filterTitle: String, genreFilter: String, platformFilter: String): List<DatabaseGame>

    @Query("SELECT * FROM games WHERE title like '%'||:filterTitle||'%'AND genre like :genreFilter AND platform like :platformFilter limit :limit offset (:page * :limit)")
    fun getFilteredPagedGames(page: Int, limit: Int,filterTitle: String, genreFilter: String, platformFilter: String): List<DatabaseGame>




    @Query("SELECT * FROM games WHERE title like '%'||:filterTitle||'%'AND genre like :genreFilter AND platform like :platformFilter AND isToPlayGame == :isToPlayGame")
    fun getFilteredToPlayGames(filterTitle: String, genreFilter: String, platformFilter: String, isToPlayGame: Boolean): List<DatabaseGame>

    @Query("SELECT * FROM games WHERE title like '%'||:filterTitle||'%'AND genre like :genreFilter AND platform like :platformFilter AND isToPlayGame == :isToPlayGame limit :limit offset (:page * :limit)")
    fun getFilteredPagedToPlayGames(page: Int, limit: Int,filterTitle: String, genreFilter: String, platformFilter: String, isToPlayGame: Boolean): List<DatabaseGame>


    @Update
    fun editGame(game: DatabaseGame)

    @Insert(onConflict = IGNORE)
    fun addGames(vararg games: DatabaseGame)

    @Delete
    fun deleteGame(game: DatabaseGame)
}
