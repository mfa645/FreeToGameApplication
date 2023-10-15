package com.example.freetogameapplication.ui.values

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalDim = compositionLocalOf { MyDimensions() }

data class  MyDimensions(
    //Spaces
    val spaceSmall: Dp = 4.dp,
    val spaceMedium: Dp = 8.dp,
    val spaceMediumLarge:Dp = 12.dp,
    val spaceLarge: Dp = 16.dp,
    val spaceLargeExtra: Dp = 20.dp,
    val spaceExtraLarge: Dp = 32.dp,
    val spaceXLarge: Dp = 42.dp,
    val spaceXXLarge: Dp = 64.dp,
    val spaceXXXLarge: Dp = 128.dp,

    val horizontalDefaultSpace: Dp = 12.dp,

    //FontSizes
    val bigTitle: TextUnit = 40.sp,
    val title: TextUnit = 40.sp,
    val subtitle: TextUnit = 26.sp,
    val bodySmall: TextUnit = 18.sp,
    val body: TextUnit = 20.sp,
    val smallFont: TextUnit = 16.sp,
    val titleDetail: TextUnit = 24.sp,

    //Icon
    val iconSize: Dp = 30.dp,
    val smallIconSize: Dp = 20.dp,
    val appIconSize: Dp = 50.dp,



    //Buttons
    val dialogButtonsWidth: Dp = 50.dp,

    //Card
    val cardElevation: Dp = 8.dp,
    val cardHeight: Dp = 320.dp,

    //Textfield
    val textFieldHeight: Dp = 40.dp,
    val filterTexFieldWidth: Dp = 200.dp,
    val filterTexFieldHeight: Dp = 60.dp,
    val dropdownMaxHeight: Dp = 330.dp,

    //Spacers
    val spacerHeight: Dp = 12.dp,
    val spacerLargeHeight: Dp = 20.dp,






    )
