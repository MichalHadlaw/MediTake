package com.example.meditake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.meditake.BottomNavBar.MainScreen
import com.example.meditake.ui.theme.MediTakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toTakeViewModel = ViewModelProvider(this)[ToTakeViewModel::class.java]
        setContent {
            MediTakeTheme {
                MainScreen()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//
//                ) {
//                    ToTakeListPage(toTakeViewModel)
                }
            }
        }
    }



