package com.example.freetogameapplication.navigation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.freetogameapplication.R


sealed class BottomBarScreens(
    val route: String,
    @StringRes  val title: Int,
    @DrawableRes val icon: Int,
){
    object Home: BottomBarScreens(
        route = NavigationRoutes.Home.route,
        title = R.string.home_title,
        icon = R.drawable.ic_home
    )
    object ToPlay: BottomBarScreens(
        route = NavigationRoutes.ToPlay.route,
        title = R.string.toplay_title,
        icon = R.drawable.ic_toplay
    )
    object Desc: BottomBarScreens(
        route = NavigationRoutes.Description.route,
        title = R.string.description_title,
        icon = R.drawable.ic_description
    )
}
