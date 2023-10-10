package com.example.data.feature.games.datastore.mappers

import com.example.data.feature.games.datastore.cache.model.DatabaseGame
import com.example.data.feature.games.datastore.remote.model.RemoteGame
import com.example.model.feature.games.Game


fun RemoteGame.toDomain(): Game =
    Game(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        gameUrl = this.gameUrl,
        shortDescription = this.shortDescription,
        genre = this.genre,
        platform = this.platform,
        publisher = this.publisher,
        developer = this.developer,
        releaseDate = this.releaseDate,
        freetogameProfileUrl = this.freetogameProfileUrl,
        isToPlayGame = false,
        toPlayString = "",
    )
fun DatabaseGame.toDomain(): Game =
    Game(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        gameUrl = this.gameUrl,
        shortDescription = this.shortDescription,
        genre = this.genre,
        platform = this.platform,
        publisher = this.publisher,
        developer = this.developer,
        releaseDate = this.releaseDate,
        freetogameProfileUrl = this.freetogameProfileUrl,
        isToPlayGame = this.isToPlayGame,
        toPlayString = this.wantToPlayDescription
    )
fun Game.toDatabase(): DatabaseGame =
    DatabaseGame(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        gameUrl = this.gameUrl,
        shortDescription = this.shortDescription,
        genre = this.genre,
        platform = this.platform,
        publisher = this.publisher,
        developer = this.developer,
        releaseDate = this.releaseDate,
        freetogameProfileUrl = this.freetogameProfileUrl,
        isToPlayGame = this.isToPlayGame,
        wantToPlayDescription = this.toPlayString,
    )