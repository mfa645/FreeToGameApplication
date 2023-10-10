package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.navigation.MainNavigation
import com.example.freetogameapplication.navigation.model.BottomBarScreens
import com.example.freetogameapplication.navigation.model.NavigationRoutes
import com.example.freetogameapp.ui.theme.DarkerGrey
import com.example.freetogameapp.ui.theme.SolidBlue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(viewModel: GamesViewModel = koinViewModel()) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(navController = navController, viewModel)
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        MainNavigation(navController = navController, paddingValues, viewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val views = BottomBarScreens::class.sealedSubclasses.mapNotNull { sealedObject ->
        sealedObject.objectInstance
    }


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = DarkerGrey) {
        views.forEach { view ->
            AddItem(
                view = view,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavHostController, viewModel: GamesViewModel = koinViewModel()) {
    val isSearching by viewModel.isSearching.collectAsState()
    val searchString by viewModel.searchText.collectAsState()


    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SolidBlue,
            titleContentColor = SolidBlue,

            ),
        title = {},
        navigationIcon = {

            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_game_console),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(NavigationRoutes.Home.route)
                        }
                        .size(50.dp)
                        .padding(8.dp)
                )
                if (!isSearching) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "FREETOGAME",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                    )
                }

            }
        },
        actions = {
            Row {
                if (!isSearching) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "Search Games!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    BasicTextField(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(end = 12.dp)
                            .align(Alignment.CenterVertically)
                            .height(40.dp)
                            .fillMaxWidth(0.7f)
                            .padding(8.dp),
                        value = searchString,
                        textStyle = TextStyle(fontSize = 16.sp),
                        onValueChange = { newSearchString ->
                            viewModel.onSearchTextChange(newSearchString)
                        },
                        maxLines = 1
                    )
                }
                IconButton(onClick = {
                    viewModel.onSearchTextChange("")
                    viewModel.onSearchingToggle()
                }) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun RowScope.AddItem(
    view: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(view.title))
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(view.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == view.route
        } == true,
        onClick = {
            navController.navigate(view.route)
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.DarkGray,
            selectedIconColor = SolidBlue,
            unselectedIconColor = Color.Gray,
            selectedTextColor = SolidBlue,
            unselectedTextColor = Color.Gray,
        )
    )
}