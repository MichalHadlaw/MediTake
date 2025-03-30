package com.example.meditake.Main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.meditake.BottomNavBar.MainScreen
import com.example.meditake.ui.theme.MediTakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicjalizacja ViewModel



        // Uruchomienie UI
        setContent {
            MediTakeTheme {
                // Przekazujemy ViewModel do MainScreen, aby miał dostęp do funkcji
                MainScreen()
            }
        }
    }
}



