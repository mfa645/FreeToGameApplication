package com.example.data.feature.services.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.feature.games.datastore.cache.dao.GamesDao
import com.example.data.feature.games.datastore.cache.model.DatabaseGame

@Database(entities = [DatabaseGame::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}