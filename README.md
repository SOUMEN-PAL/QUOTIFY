# Retrofit MVVM Offline Quotes App

This app demonstrates the implementation of Retrofit for network requests and Room for local data storage, following the MVVM architecture. The app fetches quotes from an API and stores them in a Room database for offline usage when there’s no network available.

## Features

- Fetches quotes from an API using Retrofit.
- Stores quotes in a local Room database for offline access.
- Implements MVVM architecture with Repository pattern.
- Automatic switch between online and offline modes based on network availability.

## Screenshots

<div style="display: flex; flex-direction: row;">
    <div style="flex: 33.33%; padding: 5px;">
        <p align="center">Image 1: Home Screen (Online)</p>
        <img src="https://path_to_your_image_1" alt="Home Screen (Online)" style="width: 60%;">
    </div>
    <div style="flex: 33.33%; padding: 5px;">
        <p align="center">Image 2: Offline Screen</p>
        <img src="https://path_to_your_image_2" alt="Offline Screen" style="width: 60%;">
    </div>
</div>

## Video Demo

[![Video Demo]](

https://github.com/user-attachments/assets/31ccec40-e0b7-4f4c-a6ed-0ba0447a19a6

)


https://github.com/user-attachments/assets/bba0c7e4-3cb8-4142-b19b-eae27971450a



[![Screenshot_20240905_111836](https://github.com/user-attachments/assets/758e1b61-afc9-4cb7-9aa0-8fb0acb7bf27),
![Screenshot_20240905_111817](https://github.com/user-attachments/assets/69a44632-cba4-47bd-9401-4f670dd0c27c),
![Screenshot_20240905_111756](https://github.com/user-attachments/assets/56ff1859-fe05-47c3-bb21-65048cffa630)]

<div style="display: flex;">
  <img src="https://github.com/user-attachments/assets/758e1b61-afc9-4cb7-9aa0-8fb0acb7bf27" alt="Screenshot 1" style="width: 32%; margin-right: 1%;">
  <img src="https://github.com/user-attachments/assets/69a44632-cba4-47bd-9401-4f670dd0c27c" alt="Screenshot 2" style="width: 32%; margin-right: 1%;">
  <img src="https://github.com/user-attachments/assets/56ff1859-fe05-47c3-bb21-65048cffa630" alt="Screenshot 3" style="width: 32%;">
</div>




## Project Structure

### QuotesRepository

The `QuotesRepository` is responsible for fetching quotes either from the network (via Retrofit) or from the local Room database when offline.

```kotlin
package com.example.retrofitmvvm.repository

import android.content.Context
import com.example.retrofitmvvm.api.QuoteService
import com.example.retrofitmvvm.db.QuoteDatabase
import com.example.retrofitmvvm.models.QuoteList
import com.example.retrofitmvvm.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuotesRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val _quoteState = MutableStateFlow<QuoteList?>(null)

    val quotes: StateFlow<QuoteList?> = _quoteState.asStateFlow()

    suspend fun getQuotes(page: Int) {
        if (NetworkUtils.isNetworkAvailable(applicationContext)) {
            val result = quoteService.getQuotes(page)
            if (result?.body() != null) {
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                _quoteState.value = result.body()
            }
        } else {
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            _quoteState.value = quoteList
        }
    }
}
```

## MainViewModel

The `MainViewModel` is responsible for handling the business logic of the app and interacting with the repository. It also manages the UI state, either showing loading, success with quotes, or an error.

```kotlin
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
```

## Explanation of the ViewModel

- **ViewModel**: `MainViewModel` manages the UI-related data and handles the interaction between the view and the repository. It runs background tasks using Kotlin coroutines in the `viewModelScope`.
- **QuoteListState**: A sealed class is used to represent different UI states: `Loading`, `Success`, and `Error`. This makes it easier to handle the UI states consistently.
- **Coroutine**: The `viewModelScope.launch` runs the network request on the `Dispatchers.IO` thread for better performance.
- **StateFlow**: The `quoteListState` observable is exposed to the UI layer using `StateFlow`, ensuring that the UI gets updated based on the data's state.

## DAO (Data Access Object)

The `QuoteDao` is the interface for accessing the Room database. It contains methods to insert quotes into the database and query them.

```kotlin
@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes: List<QuoteModel>)

    @Query("SELECT * FROM quotes")
    suspend fun getQuotes(): List<QuoteModel>
}
```

## Retrofit API Service

This is the interface for defining the API endpoints used by Retrofit to fetch the quotes from a remote server.

```kotlin
interface QuoteService {
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): Response<QuoteList>
}
```

## Share Feature

The app includes a feature to share quotes via other applications. Below is an example implementation in the repository class.

```kotlin
fun shareQuote(context: Context, quote: QuoteModel) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "\"${quote.text}\" - ${quote.author}")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}
```

## How to Run

1. Clone the repository:

```bash
   git clone https://github.com/SOUMEN-PAL/QUOTIFY.git
```

2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or a physical device.
4. Test the app by turning the internet connection on and off to check the online and offline modes.

## Acknowledgements

This project demonstrates the integration of Retrofit for HTTP requests, Room for local data persistence, and Kotlin coroutines with `StateFlow` for asynchronous programming in an MVVM architecture.

Feel free to explore the code, suggest improvements, or report any issues!

## Explanation of the Additional Code

- **Room Database**: The `QuoteDao` handles the insertion and querying of quotes in the local database.
- **Retrofit Service**: `QuoteService` defines the API call for fetching quotes from the server.
- **StateFlow**: The `StateFlow` is a Kotlin flow that emits updates to the UI, ensuring smooth data handling.


