package com.example.freetogameapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.freetogameapplication.feature.games.ui.composables.DescriptionView
import com.example.freetogameapplication.feature.games.ui.composables.GameDetailView
import com.example.freetogameapplication.feature.games.ui.composables.ListView
import com.example.freetogameapplication.feature.games.ui.composables.MainView
import com.example.freetogameapplication.feature.games.ui.composables.ToPlayView
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.feature.splash.SplashView
import com.example.freetogameapplication.navigation.model.NavigationRoutes
import com.example.model.feature.games.Game

@Composable
fun MainActivityNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationRoutes.Splash.route) {
        composable(NavigationRoutes.Splash.route) {
            SplashView(navController = navController)
        }

        composable("main") {
            MainView()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: GamesViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val uriHandler = LocalUriHandler.current

    val onFreeToGameUriClicked= {uri :String ->
        uriHandler.openUri(uri)
    }
    val onGameUriClicked= {uri :String ->
        uriHandler.openUri(uri)
    }

    val onStartNowButtonClicked = {
        navController.navigate(NavigationRoutes.Home.route)
    }
    val onFreeToGameButtonClicked = {
        uriHandler.openUri("https://www.freetogame.com/")
    }

    val onItemClickAction = { game: Game ->
        keyboardController?.hide()
        if (viewModel.isSearching.value) viewModel.onSearchingToggle()
        navController.navigate(NavigationRoutes.Detail.route + "${game.id}")
    }

    val onCancelAddGameToPlayList = {
        keyboardController?.hide()
        viewModel.dismissToPlayDialog()
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = NavigationRoutes.Home.route) {
            ListView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onItemClicked = onItemClickAction
            )
        }
        composable(route = NavigationRoutes.ToPlay.route) {
            ToPlayView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onItemClicked = onItemClickAction
            )
        }
        composable(route = NavigationRoutes.Description.route) {
            DescriptionView(paddingValues, onStartNowButtonClicked, onFreeToGameButtonClicked)
        }
        composable(route = NavigationRoutes.Detail.route + "{gameId}") { backStackEntry ->
            GameDetailView(
                paddingValues = paddingValues,
                viewModel = viewModel,
                gameId = desCurlyBracketizeArg(
                    backStackEntry.arguments?.getString("gameId")
                ),
                onCancelAddToPlayList = onCancelAddGameToPlayList,
                onConfirmAddGameToPlayList = { game: Game, toPlayDesc: String ->
                    keyboardController?.hide()
                    viewModel.dismissToPlayDialog()
                    game.isToPlayGame = true
                    game.toPlayString = toPlayDesc
                    viewModel.editGame(game = game)
                    navController.popBackStack()
                },
                onAddToPlayListButtonClicked = { game ->
                    if (game.isToPlayGame) {
                        game.isToPlayGame = false
                        game.toPlayString = ""
                        viewModel.editGame(game = game)
                        navController.popBackStack()
                    } else {
                        game.isToPlayGame = true
                        viewModel.showToPlayAddDialog()
                    }
                    KeyboardActions(onAny = { keyboardController?.hide() })
                },
                onFreeToGameUriClicked = onFreeToGameUriClicked,
                onGameUriClicked = onGameUriClicked
            )
        }
    }
}

fun desCurlyBracketizeArg(string: String?): String {
    if (string == null) return ""
    val result = string.replace("{", "")
    return result.replace("}", "")
}