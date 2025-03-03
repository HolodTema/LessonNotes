package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy

class TruanciesViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term

    val stateTruanciesPairs = mutableStateOf(listOf<Pair<Truancy, Subject>>())

    val textTruancy = application.resources.getString(R.string.truancy)
    val textTruancies = application.resources.getString(R.string.truancies)
    val textReason = application.resources.getString(R.string.truancies_reason)

    fun onTermGot() {
        (application as MyApplication).roomManager.getTruanciesByTermId(term.id) { truancies ->
            application.roomManager.getSubjectsForTruancies(truancies) { pairs ->
                stateTruanciesPairs.value = pairs
            }
        }
    }
}