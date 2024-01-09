package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListView(
    viewModel: GamesViewModel,
    paddingValues: PaddingValues,
    onItemClicked: (Game) -> Unit,
    onGenreFilterChange:(filter:String)->Unit,
    onPlatformFilterChange:(filter:String)->Unit
) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
    
    //val games by viewModel.games.collectAsState()
    val pagedGames = viewModel.paginatedGames.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = DarkerGrey)
    ) {

        FiltersView(viewModel,onGenreFilterChange,onPlatformFilterChange )

        if (pagedGames == null) {
            Spacer(modifier = Modifier.height(dimensions.spacerLargeHeight))
            Text(
                text = context.getString(R.string.gamelist_notfound),
                fontSize = dimensions.body,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = White
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensions.spaceMedium),
        ) {
            pagedGames?.let {
                items(
                    count = it.itemCount,
                    key = pagedGames.itemKey { game -> game.id }
                ) { gameIndex ->
                    print(gameIndex)
                    pagedGames[gameIndex]?.let{ item ->
                        GameItemView(modifier = Modifier.fillMaxWidth(), game = item) {game ->
                            onItemClicked(game)
                        }
                        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                    }
                }
            }
        }
    }
}


