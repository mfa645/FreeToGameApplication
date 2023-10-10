package com.example.data.feature.games.factory

import com.example.data.feature.games.datastore.interfaces.GamesDataStore

class GameFactory(
    val cache: GamesDataStore,
    val remote: GamesDataStore
    )