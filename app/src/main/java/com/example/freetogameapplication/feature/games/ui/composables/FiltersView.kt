package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.enums.GenreFilter
import com.example.model.feature.games.enums.PlatformFilter

@Composable
fun FiltersView(
    viewModel: GamesViewModel,
    onGenreFilterChange: (filter: String) -> Unit,
    onPlatformFilterChange: (filter: String) -> Unit
) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
    
    Row(horizontalArrangement = Arrangement.spacedBy(dimensions.horizontalDefaultSpace)) {
        val platformFilters = PlatformFilter.values().mapNotNull { platformFilter ->
            platformFilter.filter
        }
        val genreFilters = GenreFilter.values().mapNotNull { genreFilter ->
            genreFilter.filter
        }
        val spinnerPlatformValue by viewModel.platformFilter.collectAsState()
        val spinnerGenreValue by viewModel.genreFilter.collectAsState()

        var spinnerPlatformExpanded by remember {
            mutableStateOf(false)
        }

        var spinnerGenreExpanded by remember {
            mutableStateOf(false)
        }
        SpinnerDropDown(
            value = spinnerPlatformValue,
            isExpanded = spinnerPlatformExpanded,
            label = { Text(text = context.getString(R.string.platform), color = SolidBlue) },
            onExpand = { spinnerPlatformExpanded = !spinnerPlatformExpanded },
            values = platformFilters
        ) { selectedValue ->
            onPlatformFilterChange(selectedValue)
            spinnerPlatformExpanded = false
        }

        SpinnerDropDown(
            value = spinnerGenreValue,
            isExpanded = spinnerGenreExpanded,
            label = { Text(text = context.getString(R.string.genre), color = SolidBlue) },
            onExpand = { spinnerGenreExpanded = !spinnerGenreExpanded },
            values = genreFilters
        ) { selectedValue ->
            onGenreFilterChange(selectedValue)
            spinnerGenreExpanded = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SpinnerDropDown(
    value: T,
    isExpanded: Boolean,
    label: @Composable () -> Unit,
    values: List<T>,
    onExpand: () -> Unit,
    onSelected: (T) -> Unit
) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
    
    var width by remember {
        mutableStateOf(0.dp)
    }
    Box(
        modifier = Modifier.padding(dimensions.spaceMedium, end = dimensions.spaceMedium),
        contentAlignment = Alignment.TopEnd
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(dimensions.filterTexFieldWidth)
                .height(dimensions.filterTexFieldHeight)
                .graphicsLayer {
                    width = this.size.width.toDp()
                }
                .clickable { onExpand() },
            value = value.toString(),
            onValueChange = {},
            enabled = false,
            label = label,
            readOnly = true,
            textStyle = TextStyle(color = White),
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(if (isExpanded) 180f else 0f)
                        .clickable { onExpand() },
                    tint = SolidBlue
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Transparent,
                disabledBorderColor = SolidBlue
            )
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onSelected(value) },
            modifier = Modifier
                .width(width)
                .requiredSizeIn(maxHeight = dimensions.dropdownMaxHeight)
                .background(color = DarkerGrey)
                .padding(start = dimensions.spaceSmall)
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            value.toString(),
                            softWrap = false,
                            maxLines = 1,
                            color = White,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    onClick = { onSelected(value) },
                )
            }
        }
    }
}