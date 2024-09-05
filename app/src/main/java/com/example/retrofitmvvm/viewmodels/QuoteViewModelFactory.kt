package com.example.retrofitmvvm.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.repository.QuotesRepository

class QuoteViewModelFactory(var context : Context , var repository: QuotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(context , repository) as T
    }
}