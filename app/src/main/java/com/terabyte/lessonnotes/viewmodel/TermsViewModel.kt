package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class TermsViewModel(application: Application): AndroidViewModel(application) {
    val termsList = mutableStateOf<List<Term>>(listOf())

    val textTerm = application.resources.getString(R.string.term)
    val textAmountTerms = application.resources.getString(R.string.terms_amount_terms)

    init {
        (application as MyApplication).roomManager.getAllTerms {
            termsList.value = it
        }
    }

}