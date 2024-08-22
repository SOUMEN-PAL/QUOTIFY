package com.example.retrofitmvvm.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.retrofitmvvm.models.Result

data class Index(var index : Int)


class QuoteViewModel(private val context: Context) : ViewModel(){

    var quotes : List<Result> = emptyList()
    private var _index : MutableIntState = mutableIntStateOf(Index(0).index)
    var index = _index
    var size = 0

    fun getData(quoteListState: QuoteListState.Success){
        quotes = quoteListState.quoteList?.results ?: emptyList()
        size = quotes.size
    }



    fun getQuote(): Result {
        return if (quotes.isNotEmpty()) {
            quotes[index.intValue]
        } else {
            Result(1 , "xyz" , "abc" , "dfd" , "sdff" , "sfds" , "sdfs" , 0 )
        }
    }

    fun nextQuote(){
        index.intValue = (index.intValue + 1)%size
    }

    fun prevQuote(){
        index.intValue = (index.intValue - 1 + size) % size
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