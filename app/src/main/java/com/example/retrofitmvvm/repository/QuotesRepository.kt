package com.example.retrofitmvvm.repository


import com.example.retrofitmvvm.api.QuoteService
import com.example.retrofitmvvm.models.QuoteList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuotesRepository(private val quoteService: QuoteService) {

    private val _quoteState = MutableStateFlow<QuoteList?>(null)

    val quotes : StateFlow<QuoteList?> = _quoteState.asStateFlow() //asStateFLow create a read only data observable.

    suspend fun getQuotes(page : Int){
        val result = quoteService.getQuotes(page)
        if(result?.body() != null){
            _quoteState.value = result.body()
        }
    }

}