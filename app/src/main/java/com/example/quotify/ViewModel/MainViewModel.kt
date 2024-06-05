package com.example.quotify.ViewModel

import android.content.Context
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quotify.QuoteModel.QuoteModel
import com.example.quotify.QuoteModel.QuoteModelRepository

class MainViewModel(val context: Context) : ViewModel() {
    private val _repository : QuoteModelRepository = QuoteModelRepository(context)
    private val _index :MutableIntState = mutableIntStateOf(_repository.getIndex().index)
    private val _quote : MutableState<QuoteModel> = mutableStateOf(_repository.getQuote())


    val index = _index
    val quote = _quote

    fun nextQuote(){
        _repository.nextQuote()
        _index.intValue = _repository.getIndex().index
        quote.value = _repository.getQuote()
    }

    fun previousQuote(){
        _repository.previousQuote()
        _index.intValue = _repository.getIndex().index
        quote.value = _repository.getQuote()
    }


}