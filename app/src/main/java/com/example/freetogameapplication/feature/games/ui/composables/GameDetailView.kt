package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.LightGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.R
import com.example.freetogameapplication.feature.games.viewmodel.GamesViewModel
import com.example.freetogameapplication.ui.theme.LighterDarkGrey
import com.example.model.feature.games.Game

@Composable
fun GameDetailView(
    paddingValues: PaddingValues,
    viewModel: GamesViewModel,
    gameId: String,
    onAddToPlayListButtonClicked: (Game) -> Unit,
    onCancelAddToPlayList: () -> Unit,
    onConfirmAddGameToPlayList: (Game, String) -> Unit,
) {
    val istoPlayDialogShown by viewModel.showToPlayDialog.collectAsState()
    viewModel.fetchGame(gameId = gameId.toInt())
    val game by viewModel.gameDetail.collectAsState()

    if (game == null) {
        Text(text = "The game could not be found", color = Color.Red)
    } else {
        val isBothPlatforms = game?.platform == "PC (Windows), Web Browser"
        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = DarkerGrey)
                .padding(12.dp)
        )
        {
            val (titleView,
                description,
                photoView,
                platformViewFirst,
                platformViewSecond,
                button,
                toPlayDescription,
                genreView,
                dateView,
                publisherView,
                developerview,
                urlsView,
                additionalInfoView
            ) = createRefs()
            val endBarrier = createEndBarrier(platformViewFirst, platformViewSecond)

            AsyncImage(
                model = game?.thumbnail,
                contentDescription = null,
                modifier = Modifier.constrainAs(photoView) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                }
            )
            Text(
                text = game!!.title,
                modifier = Modifier.constrainAs(titleView) {
                    top.linkTo(photoView.bottom, 4.dp)
                    height = Dimension.wrapContent
                },
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontSize = 24.sp
            )
            Row(
                modifier = Modifier.constrainAs(platformViewFirst) {
                    top.linkTo(titleView.bottom, 12.dp)
                    start.linkTo(titleView.start)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (game!!.platform.contains("PC (Windows)")
                    )
                        "PC (Windows)"
                    else
                        "Web Browser",
                    color = Color.White
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id =
                        if (game!!.platform.contains("PC (Windows)"))
                            R.drawable.ic_windows
                        else
                            R.drawable.ic_browser
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(30.dp)
                )
            }

            if (isBothPlatforms) {
                Row(
                    modifier = Modifier.constrainAs(platformViewSecond) {
                        top.linkTo(titleView.bottom, 8.dp)
                        start.linkTo(platformViewFirst.end, 8.dp)
                    }, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Web Browser", color = Color.White)
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_browser
                        ),
                        contentDescription = null,
                        tint = LightGrey,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Card(shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LightGrey),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .constrainAs(genreView) {
                        start.linkTo(titleView.end, 8.dp)
                        top.linkTo(titleView.top)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent

                    }
                    .padding(4.dp)) {
                Text(
                    text = game!!.genre,
                    color = DarkerGrey,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Text(
                text = game!!.shortDescription,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(platformViewFirst.bottom, 8.dp)
                    width = Dimension.matchParent
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                textAlign = TextAlign.Justify
            )

            Text(
                text = game!!.releaseDate,
                modifier = Modifier.constrainAs(dateView) {
                    top.linkTo(description.bottom, 8.dp)
                    end.linkTo(description.end)
                    height = Dimension.wrapContent

                },
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier.constrainAs(additionalInfoView) {
                    top.linkTo(dateView.bottom, 8.dp)
                    start.linkTo(description.start)
                }, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Additional Information", color = Color.White)
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_info
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(20.dp)
                )
            }


            Text(
                text = "Publisher: " + game!!.publisher,
                modifier = Modifier.constrainAs(publisherView) {
                    top.linkTo(additionalInfoView.bottom, 8.dp)
                    start.linkTo(additionalInfoView.start)
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                textAlign = TextAlign.Justify
            )

            Text(
                text = "Developer: " + game!!.developer,
                modifier = Modifier.constrainAs(developerview) {
                    top.linkTo(publisherView.bottom, 8.dp)
                    start.linkTo(publisherView.start)
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                textAlign = TextAlign.Justify
            )
            Row(
                modifier = Modifier.constrainAs(urlsView) {
                    top.linkTo(developerview.bottom, 20.dp)
                    start.linkTo(parent.start)
                    width = Dimension.matchParent
                }, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_link
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "FreeToGame Link ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_link
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Game Url ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold
                )

            }
            Text(
                text =
                if (game!!.toPlayString.isNullOrBlank()) ""
                else
                    "ToPlay description: " + game!!.toPlayString,
                overflow = TextOverflow.Ellipsis,

                modifier = Modifier.constrainAs(toPlayDescription) {
                    top.linkTo(urlsView.bottom, 12.dp)
                    end.linkTo(button.end)
                    start.linkTo(button.start)
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = SolidBlue,
                textAlign = TextAlign.Justify
            )

            Button(
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(toPlayDescription.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!game!!.isToPlayGame) SolidBlue else Color.White,
                    contentColor = if (!game!!.isToPlayGame) Color.White else SolidBlue,
                    disabledContainerColor = LightGrey,
                    disabledContentColor = Color.White
                ),
                onClick = {
                    onAddToPlayListButtonClicked(game!!)
                }) {
                Text(
                    text = if (game!!.isToPlayGame) "Quit game from toPlayList"
                    else "Add game to ToPlay list"
                )
            }
        }
        if (istoPlayDialogShown) {
            ToPlayListDialog(
                onDismiss = onCancelAddToPlayList,
                onConfirm = onConfirmAddGameToPlayList,
                game = game!!
            )
        }
    }
}