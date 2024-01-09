package com.example.data.feature.games.factory

import com.example.data.feature.games.datastore.interfaces.LocalGamesDataStore
import com.example.data.feature.games.datastore.interfaces.RemoteGamesDataStore

class GameFactory(
    val cache: LocalGamesDataStore,
    val remote: RemoteGamesDataStore
    )