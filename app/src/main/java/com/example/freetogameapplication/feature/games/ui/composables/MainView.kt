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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainView(viewModel: GamesViewModel = koinViewModel()) {
    val navController = rememberNavController()
    
    val keyboardController = LocalSoftwareKeyboardController.current
    val uriHandler = LocalUriHandler.current

    val onSearchTextChange = { newSearchString: String ->
        viewModel.onSearchTextChange(newSearchString)
    }
    val onSearchToggle = {
        viewModel.onSearchTextChange("")
        viewModel.onSearchingToggle()
    }

    val onFreeToGameUriClicked = { uri: String ->
        uriHandler.openUri(uri)
    }
    val onGameUriClicked = { uri: String ->
        uriHandler.openUri(uri)
    }

    val onStartNowButtonClicked = {
        viewModel.setCurrentRoute(NavigationRoutes.Home.route)
        navController.navigate(NavigationRoutes.Home.route)
    }
    val onFreeToGameButtonClicked = {
        uriHandler.openUri(NavigationRoutes.FreeToGameUrlPage.route)
    }

    val onGenreFilterChange = { filter: String ->
        viewModel.setGenreFilter(filter)
    }
    val onPlatformFilterChange = { filter: String ->
        viewModel.setPlatformFilter(filter)
    }

    val onItemClickAction = { game: Game ->
        keyboardController?.hide()
        if (viewModel.isSearching.value) viewModel.onSearchingToggle()
        navController.navigate(NavigationRoutes.Detail.createRoute(game.id.toString()))
    }

    val onCancelAddGameToPlayList = {
        keyboardController?.hide()
        viewModel.dismissToPlayDialog()
    }

    val onAddToPlayListButtonClicked:(game:Game)->Unit = { game ->
        if (game.isToPlayGame) {
            game.isToPlayGame = false
            game.toPlayString = ""
            viewModel.editGame(game = game)
            navController.popBackStack()
        } else {
            game.isToPlayGame = true
            viewModel.showToPlayAddDialog()
        }

    }

    val onConfirmAddGameToPlayList:(game:Game,toPlayDesc:String)->Unit = { game,toPlayDesc ->
        keyboardController?.hide()
        viewModel.dismissToPlayDialog()
        game.isToPlayGame = true
        game.toPlayString = toPlayDesc
        viewModel.editGame(game = game)
        navController.popBackStack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(
                viewModel = viewModel,
                onAppButtonClicked = {
                    viewModel.setCurrentRoute(NavigationRoutes.Home.route)
                    if (viewModel.isSearching.value) viewModel.onSearchingToggle()
                    viewModel.setGenreFilter("")
                    viewModel.setPlatformFilter("")
                    navController.navigate(NavigationRoutes.Home.route)
                },
                onSearchTextChange = onSearchTextChange,
                onSearchToggle = onSearchToggle
            )
        },
        bottomBar = {
            BottomBar(navController = navController) { route ->
                viewModel.setCurrentRoute(route)
                if (viewModel.isSearching.value) viewModel.onSearchingToggle()
                viewModel.setGenreFilter("")
                viewModel.setPlatformFilter("")
                navController.navigate(route)
            }
        }
    ) { paddingValues ->
        MainNavigation(
            navController = navController,
            paddingValues = paddingValues,
            viewModel = viewModel,
            onFreeToGameUriClicked = onFreeToGameUriClicked,
            onGameUriClicked = onGameUriClicked,
            onStartNowButtonClicked = onStartNowButtonClicked,
            onFreeToGameButtonClicked = onFreeToGameButtonClicked,
            onGenreFilterChange = onGenreFilterChange,
            onPlatformFilterChange = onPlatformFilterChange,
            onItemClickAction = onItemClickAction,
            onCancelAddGameToPlayList = onCancelAddGameToPlayList,
            onAddToPlayListButtonClicked =onAddToPlayListButtonClicked,
            onConfirmAddGameToPlayList = onConfirmAddGameToPlayList
        )
    }
}



@Composable
fun BottomBar(
    navController: NavHostController,
    onNavButtonClicked: (route: String) -> Unit
) {
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
                onNavButtonClicked = onNavButtonClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onAppButtonClicked: () -> Unit,
    onSearchTextChange: (newText: String) -> Unit,
    onSearchToggle: () -> Unit,
    viewModel: GamesViewModel
) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
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
                            onAppButtonClicked()
                        }
                        .size(dimensions.appIconSize)
                        .padding(dimensions.spaceMedium)
                )
                if (!isSearching) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = context.getString(R.string.topbar_app_name),
                        color = White,
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
                        text = context.getString(R.string.search_games),
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    BasicTextField(
                        modifier = Modifier
                            .background(color = White, shape = RoundedCornerShape(12.dp))
                            .padding(end = dimensions.spaceMediumLarge)
                            .align(Alignment.CenterVertically)
                            .height(dimensions.textFieldHeight)
                            .fillMaxWidth(0.7f)
                            .padding(dimensions.spaceMedium),
                        value = searchString,
                        textStyle = TextStyle(fontSize = dimensions.smallFont),
                        onValueChange = { newSearchString ->
                            onSearchTextChange(newSearchString)
                        },
                        maxLines = 1
                    )
                }
                IconButton(onClick = {
                    onSearchToggle()
                }) {
                    Icon(
                        modifier = Modifier
                            .height(dimensions.iconSize)
                            .width(dimensions.iconSize),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = White
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
    onNavButtonClicked: (route: String) -> Unit
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
        onClick = { onNavButtonClicked(view.route) },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.DarkGray,
            selectedIconColor = SolidBlue,
            unselectedIconColor = Color.Gray,
            selectedTextColor = SolidBlue,
            unselectedTextColor = Color.Gray,
        )
    )
}