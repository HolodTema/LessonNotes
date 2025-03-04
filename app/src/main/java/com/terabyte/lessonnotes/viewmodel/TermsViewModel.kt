package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class TermsViewModel(private val application: Application): AndroidViewModel(application) {
    val termsList = mutableStateOf<List<Term>>(listOf())

    val textTerm = application.resources.getString(R.string.term)
    val textAmountTerms = application.resources.getString(R.string.terms_amount_terms)

    init {
        updateTermsList()
    }

    fun updateTermsList() {
        Toast.makeText(application.applicationContext, "update terms", Toast.LENGTH_SHORT).show()
        (application as MyApplication).roomManager.getAllTerms {
            termsList.value = it
        }
    }

}