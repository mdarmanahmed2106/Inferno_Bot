package com.example.inferno_bot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.inferno_bot.ui.theme.InfernoBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfernoBotTheme {
                // Call the UI from here
            }
        }
    }
}

