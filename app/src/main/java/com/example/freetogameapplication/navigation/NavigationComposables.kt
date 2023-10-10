package com.example.freetogameapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
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

@Composable
fun MainActivityNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_view") {
        composable("splash_view") {
            SplashView(navController = navController)
        }

        composable("main_view") {
            MainView()
        }
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: GamesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = NavigationRoutes.Home.route) {
            ListView(
                viewModel = viewModel,
                paddingValues = paddingValues,
            ) { game ->
                navController.navigate(NavigationRoutes.Detail.route + "${game.id}")
            }
        }
        composable(route = NavigationRoutes.ToPlay.route) {
            ToPlayView(
                paddingValues = paddingValues,
            ) { game ->
                navController.navigate(NavigationRoutes.Detail.route + "${game.id}")
            }
        }
        composable(route = NavigationRoutes.Description.route) {
            DescriptionView(paddingValues)
        }
        composable(route = NavigationRoutes.Detail.route + "{gameId}") { backStackEntry ->
            GameDetailView(
                paddingValues = paddingValues,
                viewModel = viewModel,
                gameId = desCurlyBracketizeArg(
                    backStackEntry.arguments?.getString("gameId")
                )
            ) { game ->
//TODO
            }
        }
    }
}

fun desCurlyBracketizeArg(string: String?): String {
    if (string == null) return ""
    val result = string.replace("{", "")
    return result.replace("}", "")
}