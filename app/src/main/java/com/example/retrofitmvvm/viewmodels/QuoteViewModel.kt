package com.example.retrofitmvvm.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.models.Result
import com.example.retrofitmvvm.repository.QuotesRepository
import kotlinx.coroutines.launch

data class Index(var index : Int)


class QuoteViewModel(private val context: Context , private val repository: QuotesRepository) : ViewModel(){

    var quotes : List<Result> = emptyList()
    private var _index : MutableIntState = mutableIntStateOf(Index(0).index)
    var index = _index
    var size = 0

    init {
        viewModelScope.launch {
            repository.quotes.collect { quoteList ->
                if (quoteList != null && quoteList.results.isNotEmpty()) {
                    quotes = quoteList.results
                    size = quotes.size
                    quote.value = quotes[0]
                }
            }
        }
    }


//    fun getData(quoteListState: QuoteListState.Success){
//        quotes = quoteListState.quoteList?.results ?: emptyList()
//        size = quotes.size
//    }

    var quote : MutableState<Result?> = mutableStateOf(getQuote())

    fun getQuote(): Result {
        return if (quotes.isNotEmpty()) {
            quotes[index.intValue]
        } else {
            Result(1 , "xyz" , "abc" , "dfd" , "Say Hello To Developer" , "sfds" , "sdfs" , 0 )
        }
    }

    fun nextQuote(){
        index.intValue = (index.intValue + 1)%size
        quote.value = getQuote()
    }

    fun prevQuote(){
        index.intValue = (index.intValue - 1 + size) % size
        quote.value = getQuote()
    }

    fun onShare() {
        if(quotes.isNotEmpty()){
            val quote = getQuote()
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "\"${quote.content}\" - ${quote.author}")
                type = "text/plain"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this line
            }
            context.startActivity(Intent.createChooser(sendIntent, null))
        }
    }

}