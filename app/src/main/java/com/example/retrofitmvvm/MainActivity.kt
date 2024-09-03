package com.example.retrofitmvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitmvvm.api.QuoteService
import com.example.retrofitmvvm.api.RetrofitHelper
import com.example.retrofitmvvm.db.QuoteDatabase
import com.example.retrofitmvvm.repository.QuotesRepository
import com.example.retrofitmvvm.ui.screens.HomeScreen
import com.example.retrofitmvvm.ui.theme.RetrofitMVVMTheme
import com.example.retrofitmvvm.viewmodels.MainViewModel
import com.example.retrofitmvvm.viewmodels.MainViewModelFactory
import com.example.retrofitmvvm.viewmodels.QuoteListState
import com.example.retrofitmvvm.viewmodels.QuoteViewModel
import com.example.retrofitmvvm.viewmodels.QuoteViewModelFactory
import retrofit2.create
import kotlin.math.log

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var quoteViewModel: QuoteViewModel
//    val quoteService: QuoteService = RetrofitHelper.getInstance().create(QuoteService::class.java) // Creating a quoteService Instance //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val repository = (application as QuoteApplication).quotesRepository

            mainViewModel = viewModel(factory = MainViewModelFactory(repository))
            quoteViewModel = viewModel(factory = QuoteViewModelFactory(this))
            RetrofitMVVMTheme {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    MyScreen(viewModel = mainViewModel, quoteViewModel = quoteViewModel)
                }
            }
        }
    }
}
@Composable
fun MyScreen(viewModel: MainViewModel , quoteViewModel: QuoteViewModel , modifier: Modifier = Modifier) {
    val quoteListState by viewModel.quoteListState.collectAsStateWithLifecycle()


    when (quoteListState) {
        is QuoteListState.Loading -> CircularProgressIndicator()
        is QuoteListState.Success -> {
            quoteViewModel.getData(quoteListState as QuoteListState.Success)
            HomeScreen(viewModel = quoteViewModel)

        }

        is QuoteListState.Error -> Text("Error: ${(quoteListState as QuoteListState.Error).message}")
    }
}

