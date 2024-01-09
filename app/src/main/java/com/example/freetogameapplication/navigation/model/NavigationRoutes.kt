package com.example.freetogameapplication.navigation.model

sealed class NavigationRoutes(val route: String) {
    object Home:NavigationRoutes("home")
    object ToPlay:NavigationRoutes("toplay")
    object Description:NavigationRoutes("description")
    object Detail:NavigationRoutes("detail/{gameId}") {
       fun createRoute(gameId: String) = "detail/$gameId"
    }
    object Splash:NavigationRoutes("splash")
    object Main:NavigationRoutes("main")
    object FreeToGameUrlPage:NavigationRoutes("https://www.freetogame.com/")
}