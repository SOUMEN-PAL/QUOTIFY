package com.example.retrofitmvvm.ui.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.retrofitmvvm.viewmodels.MainViewModel
import com.example.retrofitmvvm.viewmodels.QuoteListState
import com.example.retrofitmvvm.viewmodels.QuoteViewModel

@Composable
fun MyScreen(viewModel: MainViewModel, quoteViewModel: QuoteViewModel, modifier: Modifier = Modifier) {
    val quoteListState by viewModel.quoteListState.collectAsStateWithLifecycle()

    when (quoteListState) {
        is QuoteListState.Loading -> CircularProgressIndicator()
        is QuoteListState.Success -> HomeScreen(viewModel = quoteViewModel)
        is QuoteListState.Error -> Text("Error: ${(quoteListState as QuoteListState.Error).message}")
    }
}
