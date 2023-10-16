package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.PlatformFilter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameDetailView(
    paddingValues: PaddingValues,
    viewModel: GamesViewModel,
    gameId: String,
    onAddToPlayListButtonClicked: (Game) -> Unit,
    onCancelAddToPlayList: () -> Unit,
    onConfirmAddGameToPlayList: (Game, String) -> Unit,
    onFreeToGameUriClicked: (uri: String) -> Unit,
    onGameUriClicked: (uri: String) -> Unit,

    ) {
    val dimensions = LocalDim.current
    val context = LocalContext.current

    val istoPlayDialogShown by viewModel.showToPlayDialog.collectAsState()
    viewModel.fetchGame(gameId = gameId.toInt())
    val game by viewModel.gameDetail.collectAsState()

    if (game == null) {
        Text(text = context.getString(R.string.game_notfound), color = Color.Red)
    } else {
        val isBothPlatforms = game?.platform == PlatformFilter.PCAndWebBrowser.filter

        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = DarkerGrey)
                .padding(dimensions.spaceLarge)
                .verticalScroll(rememberScrollState())
        )
        {
            val (titleGenreView,
                description,
                photoView,
                platformViewFirst,
                platformViewSecond,
                button,
                toPlayDescription,
                dateView,
                publisherView,
                developerview,
                urlsView,
                additionalInfoView
            ) = createRefs()
            AsyncImage(
                model = game?.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.constrainAs(photoView) {
                    top.linkTo(parent.top, dimensions.spaceSmall)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
            FlowRow(modifier = Modifier
                .constrainAs(titleGenreView) {
                    top.linkTo(photoView.bottom, dimensions.spaceSmall)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }, horizontalArrangement = Arrangement.spacedBy(dimensions.spaceSmall)) {
                Text(
                    text = game!!.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                    fontSize = dimensions.titleDetail
                )
                Card(
                    shape = RoundedCornerShape(dimensions.horizontalDefaultSpace),
                    colors = CardDefaults.cardColors(containerColor = LightGrey),
                    elevation = CardDefaults.cardElevation(defaultElevation = dimensions.cardElevation),
                    modifier = Modifier
                        .padding(dimensions.spaceSmall)
                ) {
                    Text(
                        text = game!!.genre,
                        color = DarkerGrey,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensions.genreDetail,
                        modifier = Modifier.padding(dimensions.spaceSmall)
                    )
                }
            }

            Row(
                modifier = Modifier.constrainAs(platformViewFirst) {
                    top.linkTo(titleGenreView.bottom, dimensions.spaceMediumLarge)
                    start.linkTo(titleGenreView.start)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (game!!.platform.contains(PlatformFilter.PC.filter)
                    )
                        PlatformFilter.PC.filter
                    else
                        PlatformFilter.WebBrowser.filter,
                    color = White
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id =
                        if (game!!.platform.contains(PlatformFilter.PC.filter))
                            R.drawable.ic_windows
                        else
                            R.drawable.ic_browser
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(dimensions.iconSize)
                )
            }

            if (isBothPlatforms) {
                Row(
                    modifier = Modifier.constrainAs(platformViewSecond) {
                        top.linkTo(titleGenreView.bottom, dimensions.spaceMedium)
                        start.linkTo(platformViewFirst.end, dimensions.spaceMedium)
                    }, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = PlatformFilter.WebBrowser.filter, color = White)
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_browser
                        ),
                        contentDescription = null,
                        tint = LightGrey,
                        modifier = Modifier.size(dimensions.iconSize)
                    )
                }
            }


            Text(
                text = game!!.shortDescription,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(platformViewFirst.bottom, dimensions.spaceMedium)
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                },
                style = MaterialTheme.typography.bodyMedium,
                color = LightGrey,
                textAlign = TextAlign.Justify
            )

            Text(
                text = game!!.releaseDate,
                modifier = Modifier.constrainAs(dateView) {
                    top.linkTo(description.bottom, dimensions.spaceMedium)
                    end.linkTo(description.end)
                    height = Dimension.wrapContent

                },
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = LightGrey,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier.constrainAs(additionalInfoView) {
                    top.linkTo(dateView.bottom, dimensions.spaceMedium)
                    start.linkTo(description.start)
                }, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = context.getString(R.string.detail_addiontal_info), color = White)
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_info
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(dimensions.smallIconSize)
                )
            }


            Text(
                text = context.getString(R.string.detail_publisher) + game!!.publisher,
                modifier = Modifier.constrainAs(publisherView) {
                    top.linkTo(additionalInfoView.bottom, dimensions.spaceMedium)
                    start.linkTo(additionalInfoView.start)
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                color = LightGrey,
                textAlign = TextAlign.Justify
            )

            Text(
                text = context.getString(R.string.detail_developer) + game!!.developer,
                modifier = Modifier.constrainAs(developerview) {
                    top.linkTo(publisherView.bottom, dimensions.spaceMedium)
                    start.linkTo(publisherView.start)
                    height = Dimension.wrapContent

                },
                style = MaterialTheme.typography.bodyMedium,
                color = LightGrey,
                textAlign = TextAlign.Justify
            )
            Row(
                modifier = Modifier.constrainAs(urlsView) {
                    top.linkTo(developerview.bottom, dimensions.spaceLargeExtra)
                    start.linkTo(parent.start)
                    width = Dimension.matchParent
                }, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_link
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(dimensions.smallIconSize)
                )

                ClickableText(
                    text = AnnotatedString(context.getString(R.string.detail_freetogamelink)),
                    modifier = Modifier,
                    onClick = {
                        onFreeToGameUriClicked(game!!.freetogameProfileUrl)
                    },
                    style = TextStyle(color = LightGrey, fontWeight = FontWeight.Bold)

                )

                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_link
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(dimensions.smallIconSize)
                )

                ClickableText(
                    text = AnnotatedString(context.getString(R.string.detail_game_url)),
                    modifier = Modifier,
                    onClick = {
                        onGameUriClicked(game!!.gameUrl)
                    },
                    style = TextStyle(color = LightGrey, fontWeight = FontWeight.Bold)

                )

            }
            Text(
                text =
                if (game!!.toPlayString.isNullOrBlank()) ""
                else
                    context.getString(R.string.detail_toplay_desc) + game!!.toPlayString,
                overflow = TextOverflow.Ellipsis,

                modifier = Modifier.constrainAs(toPlayDescription) {
                    top.linkTo(urlsView.bottom, dimensions.spaceMediumLarge)
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
                    top.linkTo(toPlayDescription.bottom, dimensions.spaceLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!game!!.isToPlayGame) SolidBlue else White,
                    contentColor = if (!game!!.isToPlayGame) White else SolidBlue,
                    disabledContainerColor = LightGrey,
                    disabledContentColor = White
                ),
                onClick = {
                    onAddToPlayListButtonClicked(game!!)
                }) {
                Text(
                    text = if (game!!.isToPlayGame) context.getString(R.string.quit_to_play_button)
                    else context.getString(R.string.add_to_play_button)
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