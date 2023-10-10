package com.example.freetogameapplication.feature.games.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun DescriptionView(paddingValues: PaddingValues) {
    ConstraintLayout(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        val (
            val1,
            val2,
        ) = createRefs()
        Text(
            text = "DESCRIPCIÓN DE LA APLICACIÓN VA AQUÍ",
            modifier = Modifier.size(200.dp),
            fontSize = 20.sp
        )
    }

}