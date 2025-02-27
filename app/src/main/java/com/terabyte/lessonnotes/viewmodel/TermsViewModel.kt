package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class TermsViewModel(application: Application): AndroidViewModel(application) {
    val termsList = mutableStateOf<List<Term>>(listOf())

    init {
        (application as MyApplication).roomManager.getAllTerms {
            termsList.value = it
        }
    }

}