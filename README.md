# QUOTIFY

QUOTIFY is an app that shows you different quotes. This project showcases my proficiency in using GSON and MVVM, as well as my competence in creating a dynamic UI using Jetpack Compose and my efficiency in migrating XML UI to Jetpack Compose UI.

## Features

- Display a variety of quotes.
- Navigate through quotes using previous and next buttons.
- Share quotes via other applications.

## Screenshots

![Home Screen](./mnt/data/Q2.png)
![Share Screen](./mnt/data/Q3.png)

## Video Demo

[![QUOTIFY Video Demo](./mnt/data/QUOTIFY.mp4)](./mnt/data/QUOTIFY.mp4)

## Project Structure

### HomeScreen

This contains the main UI where quotes are displayed.

### QuoteModel

This defines the schema and the repository.

```kotlin
package com.example.quotify.QuoteModel

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import com.google.gson.Gson

data class QuoteModel(
    val text: String,
    val author: String
)

data class IndexModel(
    var index: Int
)

class QuoteModelRepository(private val context: Context) {
    private var quoteList: Array<QuoteModel> = emptyArray()
    private var indexValue = IndexModel(0)
    private var listSize = 0

    init {
        quoteList = loadQuoteFromAssets(context)
        listSize = quoteList.size
    }

    private fun loadQuoteFromAssets(context: Context): Array<QuoteModel> {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<QuoteModel>::class.java)
    }

    fun getIndex(): IndexModel {
        return indexValue
    }

    fun getQuote(): QuoteModel {
        return quoteList[indexValue.index]
    }

    fun nextQuote() {
        indexValue.index = (indexValue.index + 1) % listSize
    }

    fun previousQuote() {
        indexValue.index = (indexValue.index - 1 + listSize) % listSize
    }

    fun onShare(context: Context) {
        val quote = getQuote()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "\"${quote.text}\" - ${quote.author}")
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this line
        }
        context.startActivity(Intent.createChooser(sendIntent, null))
    }
}
