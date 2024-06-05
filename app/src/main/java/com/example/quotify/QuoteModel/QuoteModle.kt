package com.example.quotify.QuoteModel

import android.content.Context
import com.google.gson.Gson

data class QuoteModel(
    val text: String,
    val author : String
)

data class IndexModel(
    var index : Int
)

class QuoteModelRepository(private val context: Context){
    private var quoteList : Array<QuoteModel> = emptyArray()
    private var indexValue = IndexModel(0)
    private var listSize = 0;

    init{
        quoteList = loadQuoteFromAssets(context)
        listSize = quoteList.size
    }


    private fun loadQuoteFromAssets(context: Context): Array<QuoteModel> {
        val inputStream = context.assets.open("quotes.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer,Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json , Array<QuoteModel>::class.java)
    }

    fun getIndex() : IndexModel{
        return indexValue
    }

    fun getQuote(): QuoteModel {
        return quoteList[indexValue.index]
    }

    fun nextQuote(){
        indexValue.index = (indexValue.index+1)%listSize
    }

    fun previousQuote(){
        indexValue.index = (indexValue.index - 1 + listSize) % listSize
    }





}
