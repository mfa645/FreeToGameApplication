package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.freetogameapplication.R
import com.example.freetogameapplication.ui.theme.Grey
import com.example.freetogameapplication.ui.theme.LighterDarkGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game

@Composable
fun ToPlayListDialog(
    onDismiss: () -> Unit,
    onConfirm: (game: Game, toPlayDescription: String) -> Unit,
    game: Game
) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
    
    var searchString by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = dimensions.cardElevation),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = LighterDarkGrey
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                MultiColorText(
                    text1 = context.getString(R.string.dialog_question_string),
                    color1 = White,
                    text2 = " ${game.title}?",
                    color2 = SolidBlue,
                    fontSize = dimensions.body,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues()
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    modifier = Modifier
                        .background(color = Grey, shape = RoundedCornerShape(12.dp))
                        .padding(end = dimensions.spaceMediumLarge)
                        .align(Alignment.CenterHorizontally)
                        .height(dimensions.textFieldHeight)
                        .fillMaxWidth(0.95f)
                        .padding(dimensions.spaceMedium),
                    value = searchString,
                    cursorBrush = SolidColor(White),
                    textStyle = TextStyle(
                        fontSize = dimensions.smallFont,
                        color = White
                    ),
                    onValueChange = { newSearchString ->
                        searchString = newSearchString
                    },
                    maxLines = 5
                )
                Spacer(modifier = Modifier.height(dimensions.spacerHeight))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensions.spaceSmall),
                    horizontalArrangement = Arrangement.spacedBy(dimensions.horizontalDefaultSpace)
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SolidBlue
                        )
                    ) {
                        Text(
                            text = context.getString(R.string.cancel_button),
                            modifier = Modifier.width(dimensions.dialogButtonsWidth),
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = { onConfirm(game, searchString) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SolidBlue
                        )
                    ) {
                        Text(
                            text = context.getString(R.string.add_button),
                            modifier = Modifier.width(dimensions.dialogButtonsWidth),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}