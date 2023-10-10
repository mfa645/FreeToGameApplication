package com.example.data.feature.games.datastore.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.data.feature.games.datastore.cache.model.DatabaseGame

@Dao
interface GamesDao {
    @Query("SELECT * FROM games limit :limit offset :page * :limit")
    fun getAllGames(page: Int, limit: Int): List<DatabaseGame>

    @Query("SELECT * FROM games")
    fun getAllGames(): List<DatabaseGame>

    @Query("SELECT * FROM games WHERE id = :gameId LIMIT 1")
    fun getGameById(gameId: Int): DatabaseGame

    @Query("SELECT * FROM games WHERE title like '%' + :filterTitle + '%' ")
    fun getFilteredGamesByTitle(filterTitle: String): DatabaseGame

    @Query("SELECT * FROM games WHERE title like '%' + :filter + '%' limit :limit offset :page * :limit")
    fun getFilteredGamesByTitlePaged(page: Int, limit: Int, filter: String): DatabaseGame
    
    @Update
    fun editGame(game: DatabaseGame)

    @Insert(onConflict = IGNORE)
    fun addGames(vararg games: DatabaseGame)

    @Delete
    fun deleteGame(game: DatabaseGame)

}
