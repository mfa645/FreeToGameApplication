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
    viewModel: GamesViewModel,
    onFreeToGameUriClicked: (uri:String)->Unit,
    onGameUriClicked: (uri:String)->Unit,
    onFreeToGameButtonClicked:()->Unit,
    onStartNowButtonClicked:()->Unit,
    onGenreFilterChange:(filter:String)->Unit,
    onPlatformFilterChange:(filter:String)->Unit,
    onItemClickAction:(game: Game)->Unit,
    onCancelAddGameToPlayList:()->Unit,
    onAddToPlayListButtonClicked:(game: Game)->Unit,
    onConfirmAddGameToPlayList:(game:Game, toPlayDesc:String)->Unit

) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = NavigationRoutes.Home.route) {
            ListView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onItemClicked = onItemClickAction,
                onGenreFilterChange= onGenreFilterChange,
                onPlatformFilterChange = onPlatformFilterChange
            )
        }
        composable(route = NavigationRoutes.ToPlay.route) {
            ToPlayView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onItemClicked = onItemClickAction,
                onGenreFilterChange= onGenreFilterChange,
                onPlatformFilterChange = onPlatformFilterChange
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
                onConfirmAddGameToPlayList = onConfirmAddGameToPlayList,
                onAddToPlayListButtonClicked = onAddToPlayListButtonClicked,
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