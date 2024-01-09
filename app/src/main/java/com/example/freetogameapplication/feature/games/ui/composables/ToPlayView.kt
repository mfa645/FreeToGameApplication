package com.example.freetogameapplication.feature.games.ui.composables

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game


@Composable
fun ToPlayView(
    viewModel: GamesViewModel,
    paddingValues: PaddingValues,
    onItemClicked: (Game) -> Unit,
    onGenreFilterChange: (filter: String) -> Unit,
    onPlatformFilterChange: (filter: String) -> Unit

) {
    val dimensions = LocalDim.current
    val context = LocalContext.current

    viewModel.fetchFilteredToPlayGames()
    val games = viewModel.paginatedToPlayGames.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = DarkerGrey)
    ) {
        FiltersView(viewModel, onGenreFilterChange, onPlatformFilterChange)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensions.spaceMedium),
        ) {
            item {
                MultiColorText(
                    text1 = context.getString(R.string.toplay_header_one),
                    color1 = White,
                    text2 = " " + context.getString(R.string.toplay_header_two),
                    color2 = SolidBlue,
                    fontSize = dimensions.body,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(horizontal = dimensions.spaceSmall)
                )
                Spacer(modifier = Modifier.height(dimensions.spacerHeight))
                if (games.itemCount == 0) {
                    Text(
                        text = context.getString(R.string.toplay_games_notfound),
                        fontSize = dimensions.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = White
                    )
                }
            }
            items(
                count = games.itemCount,
                key = games.itemKey { game -> game.id }
            ) { gameIndex ->
                print(gameIndex)
                games[gameIndex]?.let { item ->
                    GameItemView(modifier = Modifier.fillMaxWidth(), game = item) { game ->
                        onItemClicked(game)
                    }
                    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
                }
            }
        }
    }
}

@Composable
fun MultiColorText(
    text1: String,
    color1: Color,
    text2: String,
    color2: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    paddingValues: PaddingValues
) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = color1)) {
                append(text1)
            }
            withStyle(style = SpanStyle(color = color2)) {
                append(text2)
            }
        },
        fontSize = fontSize,
        modifier = Modifier.padding(paddingValues),
        fontWeight = fontWeight
    )
}