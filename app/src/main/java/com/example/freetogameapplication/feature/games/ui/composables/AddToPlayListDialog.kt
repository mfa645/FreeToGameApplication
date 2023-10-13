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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.freetogameapplication.ui.theme.Grey
import com.example.freetogameapplication.ui.theme.LighterDarkGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.model.feature.games.Game

@Composable
fun ToPlayListDialog(
    onDismiss: () -> Unit,
    onConfirm: (game: Game, toPlayDescription: String) -> Unit,
    game: Game
) {
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
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
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
                    text1 = "Why you would like to play ",
                    color1 = Color.White,
                    text2 = "${game.title}?",
                    color2 = SolidBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    paddingValues = PaddingValues()
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    modifier = Modifier
                        .background(color = Grey, shape = RoundedCornerShape(12.dp))
                        .padding(end = 12.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(40.dp)
                        .fillMaxWidth(0.95f)
                        .padding(8.dp),
                    value = searchString,
                    cursorBrush = SolidColor(Color.White),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                    onValueChange = { newSearchString ->
                        searchString = newSearchString
                    },
                    maxLines = 5
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SolidBlue
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            modifier = Modifier.width(50.dp),
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
                            text = "Add",
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}