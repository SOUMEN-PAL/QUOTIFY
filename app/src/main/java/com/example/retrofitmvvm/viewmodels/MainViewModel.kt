package com.example.retrofitmvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.models.QuoteList
import com.example.retrofitmvvm.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuotesRepository): ViewModel() {
    private val _quoteListState = MutableStateFlow<QuoteListState>(QuoteListState.Loading)
    val quoteListState: StateFlow<QuoteListState> = _quoteListState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getQuotes(1)
                _quoteListState.value = QuoteListState.Success(repository.quotes.value)
            } catch (e: Exception) {
                _quoteListState.value = QuoteListState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class QuoteListState {
    object Loading : QuoteListState()
    data class Success(val quoteList: QuoteList?) : QuoteListState()
    data class Error(val message: String) : QuoteListState()
}