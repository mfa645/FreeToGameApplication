package com.example.freetogameapplication.feature.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature.games.AddGamesUseCase
import com.example.domain.feature.games.AllGamesUseCase
import com.example.domain.feature.games.EditGameUseCase
import com.example.domain.feature.games.GetFilteredGamesUseCase
import com.example.domain.feature.games.GetGameUseCase
import com.example.freetogameapplication.navigation.model.NavigationRoutes
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class GamesViewModel(
    private val allGamesUseCase: AllGamesUseCase,
    private val getGameUseCase: GetGameUseCase,
    private val addGamesUseCase: AddGamesUseCase,
    private val editGameUseCase: EditGameUseCase,
    private val getFilteredGamesUseCase: GetFilteredGamesUseCase
) : ViewModel() {

    private val _currentRoute = MutableStateFlow(NavigationRoutes.Home.route)
    private val currentRoute = _currentRoute.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _genreFilter = MutableStateFlow("")
    val genreFilter = _genreFilter.asStateFlow()

    private val _platformFilter = MutableStateFlow("")
    val platformFilter = _platformFilter.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _showToPlayDialog = MutableStateFlow(false)
    val showToPlayDialog = _showToPlayDialog.asStateFlow()

    private val _gameDetail = MutableStateFlow<Game?>(null)
    val gameDetail = _gameDetail.asStateFlow()

    private val _games = MutableStateFlow(listOf<Game>())
    val games = _games.asStateFlow()

    private val _toPlayGames = MutableStateFlow(listOf<Game>())
    val toPlayGames = _toPlayGames.asStateFlow()

    init {
        fetchApiGames()
        fetchGames()
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
                withContext(Dispatchers.Main) {
                    _games.value = localData
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun fetchFilteredGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filteredData = getFilteredGamesUseCase.invoke(
                    searchText.value,
                    genreFilter.value,
                    platformFilter.value,
                    isToPlayGames = false
                )
                withContext(Dispatchers.Main) {
                    _games.value = filteredData
                }
            } catch (e: Exception) {
            }
        }
    }

    fun fetchFilteredToPlayGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filteredData = getFilteredGamesUseCase.invoke(
                    searchText.value,
                    genreFilter.value,
                    platformFilter.value,
                    isToPlayGames = true
                )
                withContext(Dispatchers.Main) {
                    _toPlayGames.value = filteredData
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

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        if (currentRoute.value == NavigationRoutes.Home.route) {
            fetchFilteredGames()
        } else {
            fetchFilteredToPlayGames()
        }
    }

    fun onSearchingToggle() {
        _searchText.value = ""
        _isSearching.value = !isSearching.value
    }

    fun showToPlayAddDialog() {
        _showToPlayDialog.value = true
    }

    fun dismissToPlayDialog() {
        _showToPlayDialog.value = false
    }

    fun editGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                editGameUseCase.invoke(game)
            } catch (e: Exception) {
            }
        }
    }

    fun setCurrentRoute(route: String) {
        _currentRoute.value = route
    }

    fun setPlatformFilter(filter: String) {
        _platformFilter.value = filter
        if(currentRoute.value==NavigationRoutes.Home.route){
            fetchFilteredGames()
        }
        if(currentRoute.value==NavigationRoutes.ToPlay.route){
            fetchFilteredToPlayGames()
        }    }

    fun setGenreFilter(filter: String) {
        _genreFilter.value = filter
        if(currentRoute.value==NavigationRoutes.Home.route){
            fetchFilteredGames()
        }
        if(currentRoute.value==NavigationRoutes.ToPlay.route){
            fetchFilteredToPlayGames()
        }
    }
}