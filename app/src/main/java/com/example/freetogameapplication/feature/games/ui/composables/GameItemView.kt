package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.freetogameapp.ui.theme.LightGrey
import com.example.freetogameapp.ui.theme.LighterDarkGrey
import com.example.freetogameapplication.R
import com.example.model.feature.games.Game


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameItemView(
    modifier: Modifier = Modifier,
    game: Game,
    onClick: (Game) -> Unit = {}
) {
    val isBothPlatforms = game.platform == "PC (Windows), Web Browser"
    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LighterDarkGrey),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onClick(game) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(8.dp)
        )
        {
            val (titleView,
                description,
                photoView,
                platformViewFirst,
                platformViewSecond
            ) = createRefs()
            val endBarrier = createEndBarrier(platformViewFirst, platformViewSecond)

            AsyncImage(
                model = game.thumbnail,
                contentDescription = null,
                modifier = Modifier.constrainAs(photoView) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                }
            )
            Text(
                text = game.title,
                modifier = Modifier.constrainAs(titleView) {
                    top.linkTo(photoView.bottom, 4.dp)
                    height = Dimension.wrapContent
                },
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
            Icon(
                imageVector = ImageVector.vectorResource(

                    id =
                    if (game.platform.contains("PC (Windows)"))
                        R.drawable.ic_windows
                    else
                        R.drawable.ic_browser
                ),
                contentDescription = null,
                tint = LightGrey,
                modifier = Modifier.constrainAs(platformViewFirst) {
                    top.linkTo(titleView.top)
                    bottom.linkTo(titleView.bottom)
                    start.linkTo(titleView.end, 8.dp)
                    end.linkTo(endBarrier)
                    width = Dimension.value(30.dp)
                    height = Dimension.value(30.dp)
                }
            )
            if (isBothPlatforms) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_browser
                    ),
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.constrainAs(platformViewSecond) {
                        top.linkTo(titleView.top)
                        bottom.linkTo(titleView.bottom)
                        start.linkTo(platformViewFirst.end, 8.dp)
                        end.linkTo(endBarrier)
                        width = Dimension.value(30.dp)
                        height = Dimension.value(30.dp)
                    }
                )
            }

            Text(
                text = game.shortDescription,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(titleView.bottom, 8.dp)
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
    Spacer(modifier = Modifier.height(8.dp))
}