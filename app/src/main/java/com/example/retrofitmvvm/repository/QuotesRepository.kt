package com.example.retrofitmvvm.repository


import android.content.Context
import com.example.retrofitmvvm.api.QuoteService
import com.example.retrofitmvvm.db.QuoteDatabase
import com.example.retrofitmvvm.models.QuoteList
import com.example.retrofitmvvm.utls.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuotesRepository(private val quoteService: QuoteService , private val quoteDatabase: QuoteDatabase , private val applicationContext : Context) {

    private val _quoteState = MutableStateFlow<QuoteList?>(null)

    val quotes : StateFlow<QuoteList?> = _quoteState.asStateFlow() //asStateFLow create a read only data observable.

    suspend fun getQuotes(page : Int){

        if(NetworkUtils.isNetworkAvailable(applicationContext)){
            val result = quoteService.getQuotes(page)
            if(result?.body() != null){
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                _quoteState.value = result.body()
            }
        }
        else{
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quotes , 1,1)
            _quoteState.value = quoteList
        }



    }

}