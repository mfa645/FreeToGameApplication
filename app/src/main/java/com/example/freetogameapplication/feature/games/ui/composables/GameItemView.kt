package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.freetogameapplication.ui.theme.LightGrey
import com.example.freetogameapplication.ui.theme.LighterDarkGrey
import com.example.freetogameapplication.R
import com.example.freetogameapplication.ui.values.LocalDim
import com.example.model.feature.games.Game
import com.example.model.feature.games.enums.PlatformFilter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GameItemView(
    modifier: Modifier = Modifier,
    game: Game,
    onClick: (Game) -> Unit = {}
) {
    val dimensions = LocalDim.current

    val isBothPlatforms = game.platform == PlatformFilter.PCAndWebBrowser.filter
    Card(
        modifier = modifier.padding(horizontal = dimensions.spaceSmall),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LighterDarkGrey),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensions.cardElevation),
        onClick = { onClick(game) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.cardHeight)
                .padding(dimensions.spaceMedium)
        )
        {
            val (
                titlePlatformView,
                description,
                photoView,
            ) = createRefs()

            AsyncImage(
                model = game.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(photoView) {
                        top.linkTo(parent.top)
                        width = Dimension.matchParent
                    }
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            FlowRow(modifier = Modifier.constrainAs(titlePlatformView) {
                top.linkTo(photoView.bottom, dimensions.spaceSmall)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }, horizontalArrangement = Arrangement.spacedBy(dimensions.spaceSmall)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id =
                        if (game.platform.contains(PlatformFilter.PC.filter))
                            R.drawable.ic_windows
                        else
                            R.drawable.ic_browser
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(dimensions.iconSize)
                )
                if (isBothPlatforms) {
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
                text = game.shortDescription,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(titlePlatformView.bottom, dimensions.spaceMedium)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints

                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray,
                textAlign = TextAlign.Justify
            )
        }
    }
    Spacer(modifier = Modifier.height(dimensions.spaceMedium))
}
