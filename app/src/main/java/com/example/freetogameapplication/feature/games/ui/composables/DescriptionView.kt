package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.freetogameapplication.R
import com.example.freetogameapplication.ui.theme.LightGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.White
import com.example.freetogameapplication.ui.values.LocalDim


@Composable
fun DescriptionView(
    paddingValues: PaddingValues,
    onButtonClicked: () -> Unit,
    onFreeToGameButtonClicked: () -> Unit,

) {
    val dimensions = LocalDim.current
    val context = LocalContext.current
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
            alpha = 0.3f,
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
            text = context.getString(R.string.descriptionview_title),
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, dimensions.spaceExtraLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontSize = dimensions.bigTitle,
            color = SolidBlue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp,
        )
        Text(
            text = context.getString(R.string.descriptionview_subtitle),
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, dimensions.spaceLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.9f)
                },
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Bold,
            fontSize = dimensions.subtitle,
            lineHeight = 32.sp,
            color = White,
        )
        Text(
            text = context.getString(R.string.descriptionview_body),
            modifier = Modifier
                .constrainAs(body) {
                    top.linkTo(subtitle.bottom, dimensions.spaceLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.9f)
                },
            fontSize = dimensions.body,
            color = LightGrey,
            lineHeight = 35.sp,
            textAlign = TextAlign.Justify,
        )

        Row(
            modifier = Modifier.constrainAs(buttonsView) {
                top.linkTo(body.bottom, dimensions.spaceLarge)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            horizontalArrangement = Arrangement.spacedBy(dimensions.horizontalDefaultSpace)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = SolidBlue,
                    contentColor = White,
                    disabledContainerColor = LightGrey,
                    disabledContentColor = White
                ),
                onClick =
                onButtonClicked
            ) {
                Text(
                    text = context.getString(R.string.descriptionview_start_button),
                    fontSize = dimensions.body
                )
            }
            OutlinedButton(
                border = BorderStroke(0.5.dp, White),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = White),
                onClick = onFreeToGameButtonClicked
            ) {
                Text(
                    text = context.getString(R.string.descriptionview_freetogame_button),
                    fontSize = dimensions.body
                )
            }
        }
    }

}