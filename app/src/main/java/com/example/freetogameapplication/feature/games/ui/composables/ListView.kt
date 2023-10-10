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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapp.ui.theme.DarkerGrey
import com.example.model.feature.games.Game


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListView(
    viewModel: GamesViewModel,
    paddingValues: PaddingValues,
    onItemClicked: (Game) -> Unit
) {
    val games by viewModel.games.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding()) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = DarkerGrey)
                .padding(top = 8.dp),
        ) {
            items(games) { game ->
                GameItemView(modifier = Modifier.fillMaxWidth(), game = game) {
                    onItemClicked(game)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
