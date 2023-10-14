package com.example.freetogameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.freetogameapplication.navigation.MainActivityNavigation
import com.example.freetogameapplication.ui.theme.DarkerGrey
import com.example.freetogameapplication.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.FreeToGameApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeToGameApplicationTheme {
                SetStatusBarColor(statusBarColor = SolidBlue, navigationBarColor = DarkerGrey)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SolidBlue
                ) {
                    MainActivityNavigation()
                }
            }
        }
    }

    @Composable
    fun SetStatusBarColor(statusBarColor: Color, navigationBarColor: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(statusBarColor)
            systemUiController.setNavigationBarColor(navigationBarColor)
        }
    }

}
