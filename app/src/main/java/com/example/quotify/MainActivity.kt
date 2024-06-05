package com.example.quotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quotify.ViewModel.MainViewModel
import com.example.quotify.ViewModel.MainViewModelFactory
import com.example.quotify.appscreens.HomeScreen
import com.example.quotify.ui.theme.QuotifyTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(application))

            QuotifyTheme {
                HomeScreen(viewModel = mainViewModel)
            }
        }
    }
}
