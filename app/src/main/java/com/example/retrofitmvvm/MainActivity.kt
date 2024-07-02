package com.example.retrofitmvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitmvvm.api.QuoteService
import com.example.retrofitmvvm.api.RetrofitHelper
import com.example.retrofitmvvm.db.QuoteDatabase
import com.example.retrofitmvvm.repository.QuotesRepository
import com.example.retrofitmvvm.ui.theme.RetrofitMVVMTheme
import com.example.retrofitmvvm.viewmodels.MainViewModel
import com.example.retrofitmvvm.viewmodels.MainViewModelFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
//    val quoteService: QuoteService = RetrofitHelper.getInstance().create(QuoteService::class.java) // Creating a quoteService Instance //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val repository = (application as QuoteApplication).quotesRepository

            mainViewModel = viewModel(factory = MainViewModelFactory(repository))
            val quotes by mainViewModel.quoteList.collectAsStateWithLifecycle()

            RetrofitMVVMTheme {
//                quotes?.results?.let { results ->
//                    for (i in results) {
//                        Log.d("Data", i.content)
//                    }
//                }
                Log.d("data" , quotes?.results.toString())
            }
        }
    }
}
