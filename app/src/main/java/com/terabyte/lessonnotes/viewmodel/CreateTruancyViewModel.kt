package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy
import com.terabyte.lessonnotes.util.DateHelper

class CreateTruancyViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term
    lateinit var subjects: List<Subject>

    val stateTruancyReason = mutableStateOf("")
    val stateTruancyDate = mutableStateOf(DateHelper.getCurrentDate())
    val stateTruancyParentSubjectId = mutableStateOf(0)
    val stateChosenSubject = mutableStateOf<Subject?>(null)

    val stateShowDialogChangeSubject = mutableStateOf(false)
    val stateShowDateDialog = mutableStateOf(false)

    fun onTermGot() {
        (application as MyApplication).roomManager.getSubjectsByTermId(term.id) {
            subjects = it
            //TODO in termInfoActivity button with truancies must work only if there are subjects > 0
            stateChosenSubject.value = subjects[0]
        }
    }

    fun createTruancy() {
        val truancy = Truancy(
            id = 0,
            parentTermId = term.id,
            subjectId = stateChosenSubject.value!!.id,
            reason = stateTruancyReason.value,
            date = stateTruancyDate.value
        )
        (application as MyApplication).roomManager.insertTruancy(truancy)
    }
}