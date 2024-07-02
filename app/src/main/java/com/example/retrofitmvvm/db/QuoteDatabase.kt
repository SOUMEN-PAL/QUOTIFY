package com.example.retrofitmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitmvvm.models.Result
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

@Database(entities = [Result::class] , version = 1)
abstract class QuoteDatabase:RoomDatabase() {

    abstract fun quoteDao() : QuoteDAO

    companion object{

        @Volatile
        private var INSTANCE:QuoteDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): QuoteDatabase? {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        QuoteDatabase::class.java ,
                        "quoteDB")
                        .build()
                }
            }
            return  INSTANCE!!
        }

    }

}