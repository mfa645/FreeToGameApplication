package com.example.data.feature.games.datastore.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
class DatabaseGame(
        @PrimaryKey val id: Int,
        val title: String,
        val thumbnail: String,
        val shortDescription: String,
        val gameUrl: String,
        val genre: String,
        val platform: String,
        val publisher: String,
        val developer: String,
        val releaseDate: String,
        val freetogameProfileUrl: String,
        val isToPlayGame : Boolean,
        val wantToPlayDescription: String,
    )