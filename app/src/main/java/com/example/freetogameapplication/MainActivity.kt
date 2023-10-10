package com.example.freetogameapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.freetogameapplication.navigation.MainActivityNavigation
import com.example.freetogameapp.ui.theme.SolidBlue
import com.example.freetogameapplication.ui.theme.FreeToGameApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeToGameApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SolidBlue
                ) {
                    MainActivityNavigation()
                }
            }
        }
    }
}