package com.terabyte.lessonnotes.room

import android.content.Context
import androidx.room.Room
import com.terabyte.lessonnotes.config.ROOM_DB_NAME
import com.terabyte.lessonnotes.room.entity.Term
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RoomManager(context: Context) {

    private val database: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, ROOM_DB_NAME)
            .build()

    fun insertTerm(term: Term, listener: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().insertTerm(term)
        }
    }

    fun updateTerm(term: Term, listener: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().updateTerm(term)
        }
    }

    fun deleteTerm(term: Term) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().deleteTerm(term)
        }
    }

    fun getAllTerms(listener: (List<Term>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.termDao().getAllTerms()
            }
            listener(deferred.await())
        }
    }
}