package com.example.freetogameapplication.feature.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature.games.AddGamesUseCase
import com.example.domain.feature.games.AllGamesUseCase
import com.example.domain.feature.games.EditGameUseCase
import com.example.domain.feature.games.GetGameUseCase
import com.example.model.feature.games.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class GamesViewModel(
    private val allGamesUseCase: AllGamesUseCase,
    private val getGameUseCase: GetGameUseCase,
    private val addGamesUseCase: AddGamesUseCase,
    private val editGameUseCase: EditGameUseCase

) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _gameDetail = MutableStateFlow<Game?>(null)
    val gameDetail = _gameDetail.asStateFlow()

    private val _games = MutableStateFlow(listOf<Game>())
    val games: StateFlow<List<Game>> = searchText
        .combine(_games) { text, games ->
            if (text.isBlank()) {
                games
            } else {
                games.filter {
                    doesMatchSearchQuery(text, it)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _games.value
        )
    private val _toPlayGames = MutableStateFlow(listOf<Game>())
    val toPlayGames = _toPlayGames.asStateFlow()


    init {
        fetchApiGames()
        fetchGames()
        fetchToPlayGames()
    }
    private fun fetchApiGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiData = allGamesUseCase.invoke(isLocal = false)
                addGamesUseCase.invoke(*apiData.toTypedArray())
            } catch (e: Exception) {
            }
        }
    }

    private fun fetchGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val localData = allGamesUseCase.invoke(isLocal = true)
                withContext(Dispatchers.Main){
                    _games.value = localData
                }
            } catch (e: Exception) {
            }
        }
    }
    private fun fetchToPlayGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val localData = allGamesUseCase.invoke(isLocal = true)
                withContext(Dispatchers.Main){
                    _games.value = localData
                }
            } catch (e: Exception) {
            }
        }
    }


    fun fetchGame(gameId: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = getGameUseCase.invoke(gameId)
                withContext(Dispatchers.Main) {
                    _gameDetail.value = data
                }
            } catch (e: Exception) {
            }
        }
    }


    private fun doesMatchSearchQuery(query: String, game: Game): Boolean {
        val matchingCombinations = listOf(
            game.title,
            game.title.replace(" ", "")
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onSearchingToggle() {
        _isSearching.value = !isSearching.value
    }

    fun editGame(){

    }


}