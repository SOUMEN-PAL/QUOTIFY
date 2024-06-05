# QUOTIFY

QUOTIFY is an app that shows you different quotes. This project showcases my proficiency in using GSON and MVVM, as well as my competence in creating a dynamic UI using Jetpack Compose and my efficiency in migrating XML UI to Jetpack Compose UI.

## Features

- Display a variety of quotes.
- Navigate through quotes using previous and next buttons.
- Share quotes via other applications.

## Screenshots

![Home Screen]![Q1](https://github.com/SOUMEN-PAL/QUOTIFY/assets/112452467/06e529fe-80c2-460f-a2ad-287fe5750074)

![Share Screen]![Q2](https://github.com/SOUMEN-PAL/QUOTIFY/assets/112452467/ffe90d48-99e9-43bb-8a16-a1a8513b6ef5)

![MVVM Depiction]![Q3](https://github.com/SOUMEN-PAL/QUOTIFY/assets/112452467/d78ab0ef-b5d0-4d1e-bf14-b15e0d29fd16)


## Video Demo

[![QUOTIFY Video Demo]

https://github.com/SOUMEN-PAL/QUOTIFY/assets/112452467/3d3abbd2-feb3-4508-95d6-f29beeabe712



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
```
# MainViewModel

This contains the business logic.

```kotlin
package com.example.quotify.ViewModel

import android.content.Context
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.quotify.QuoteModel.QuoteModel
import com.example.quotify.QuoteModel.QuoteModelRepository

class MainViewModel(Appcontext: Context) : ViewModel() {
    private val _repository: QuoteModelRepository = QuoteModelRepository(Appcontext)
    private val _index: MutableIntState = mutableIntStateOf(_repository.getIndex().index)
    private val _quote: MutableState<QuoteModel> = mutableStateOf(_repository.getQuote())

    val index = _index
    val quote = _quote

    fun nextQuote() {
        _repository.nextQuote()
        _index.intValue = _repository.getIndex().index
        quote.value = _repository.getQuote()
    }

    fun previousQuote() {
        _repository.previousQuote()
        _index.intValue = _repository.getIndex().index
        quote.value = _repository.getQuote()
    }

    fun onShare(Activitycontext: Context) {
        _repository.onShare(Activitycontext)
    }
}
```
# Share Feature

**QUOTIFY** includes a share feature that allows users to share quotes via other applications. Below is the code implementation of the share feature.

## Share Feature Implementation

In `QuoteModelRepository`, the `onShare` function is implemented as follows:

```kotlin
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
```
## In MainViewModel, the `onShare` function is called to share the quote:

```kotlin
fun onShare(Activitycontext: Context) {
    _repository.onShare(Activitycontext)
}
```

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Acknowledgements

This project demonstrates the integration of GSON for JSON parsing and the MVVM architecture pattern with Jetpack Compose for the UI.

Feel free to explore the code and provide any feedback or suggestions!


