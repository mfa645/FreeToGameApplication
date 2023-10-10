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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapp.ui.theme.DarkerGrey
import com.example.freetogameapp.ui.theme.SolidBlue
import com.example.model.feature.games.Game
import org.koin.androidx.compose.koinViewModel


@Composable
fun ToPlayView(
    viewModel: GamesViewModel,
    paddingValues: PaddingValues,
    onItemClicked: (Game) -> Unit
) {
    val games by viewModel.toPlayGames.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding()) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = DarkerGrey)
                .padding(top = 8.dp),
        ) {
            item {
                MultiColorText(
                    text1 = "Games you ",
                    color1 = Color.White,
                    text2 = "need to play!",
                    color2 = SolidBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (games.isNullOrEmpty()) {
                    Text(
                        text = "No ToPlay games added yet!",
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
            items(games) { game ->
                GameItemView(modifier = Modifier.fillMaxWidth(), game = game) {
                    onItemClicked(game)
                }
                Spacer(modifier = Modifier.height(8.dp))
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