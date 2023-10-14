package com.example.freetogameapplication.feature.games.ui.composables

import android.graphics.ColorMatrixColorFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.freetogameapplication.R
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.LightGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.SolidGrey
import com.example.freetogameapplication.ui.theme.TransparentDarkerGrey


@Composable
fun DescriptionView(
    paddingValues: PaddingValues,
    onButtonClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.Black)
    ) {
        val (
            title,
            subtitle,
            body,
            buttonsView,
            background,
        ) = createRefs()

        AsyncImage(model = R.drawable.img_description_wallpaper,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
            modifier = Modifier.constrainAs(background) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })

        Text(
            text = "FREETOGAME APPLICATION!",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, 42.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontSize = 40.sp,
            color = SolidBlue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp,
        )
        Text(
            text = "Track your favourite games, make a list of what to play and search for more!",
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.9f)
                },
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 32.sp,
            color = Color.White,
        )
        Text(
            text = "FreeToGame application allows you to discover all the best Free-to-Play Multiplayer Games and MMO Games into one place, letting you make a personalized list of your own games library.",
            modifier = Modifier
                .constrainAs(body) {
                    top.linkTo(subtitle.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.9f)
                },
            fontSize = 20.sp,
            color = LightGrey,
            lineHeight = 35.sp,
            textAlign = TextAlign.Justify,
        )

        Row(
            modifier = Modifier.constrainAs(buttonsView) {
                top.linkTo(body.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = SolidBlue,
                    contentColor = Color.White,
                    disabledContainerColor = LightGrey,
                    disabledContentColor = Color.White
                ),
                onClick =
                onButtonClicked
            ) {
                Text(
                    text = "Start Now!",
                    fontSize = 20.sp
                )
            }
            OutlinedButton(
                border = BorderStroke(0.5.dp, Color.White),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                onClick = onButtonClicked
            ) {
                Text(
                    text = "FreeToGame",
                    fontSize = 20.sp
                )
            }
        }
    }

}