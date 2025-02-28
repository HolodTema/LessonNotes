package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term

class SubjectInfoViewModel(application: Application): AndroidViewModel(application) {
    lateinit var term: Term
    lateinit var subject: Subject

    fun onExtrasGot() {

    }

}