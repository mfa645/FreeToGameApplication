package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.navigation.model.BottomBarScreens
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.LightGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter


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
    
    val games by viewModel.games.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = DarkerGrey)
    ) {

        FiltersView(viewModel,onGenreFilterChange,onPlatformFilterChange )

        if (games.isNullOrEmpty()) {
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
            items(games) { game ->
                GameItemView(modifier = Modifier.fillMaxWidth(), game = game) {
                    onItemClicked(game)
                }
                Spacer(modifier = Modifier.height(dimensions.spaceMedium))
            }
        }
    }
}


