package com.example.model.feature.games

data class Game(
    val id: Int =-1,
    val title: String,
    val thumbnail: String,
    val gameUrl: String,
    val shortDescription: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freetogameProfileUrl: String,
    var isToPlayGame: Boolean = false,
    val toPlayString : String = "",
)