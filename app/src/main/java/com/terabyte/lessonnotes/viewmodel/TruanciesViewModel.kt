package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy

class TruanciesViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term

    val stateTruancies = mutableStateOf(listOf<Truancy>())

    fun onTermGot() {
        (application as MyApplication).roomManager.getTruanciesByTermId(term.id) {
            stateTruancies.value = it
        }
    }
}