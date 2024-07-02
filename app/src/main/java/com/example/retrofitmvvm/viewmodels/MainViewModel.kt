package com.example.retrofitmvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.models.QuoteList
import com.example.retrofitmvvm.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuotesRepository):ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quoteList : StateFlow<QuoteList?> = repository.quotes
}