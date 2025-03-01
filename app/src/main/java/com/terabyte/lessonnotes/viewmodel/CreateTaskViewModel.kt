package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.util.DateHelper
import java.util.Calendar

class CreateTaskViewModel(application: Application): AndroidViewModel(application) {
    lateinit var term: Term
    lateinit var subject: Subject

    val stateTaskName = mutableStateOf("")
    val stateTaskDescription = mutableStateOf("")
    val stateTaskDate = mutableStateOf(DateHelper.getCurrentDate())
    val stateTaskImportance = mutableStateOf(1)
    val stateShowDateDialog = mutableStateOf(false)

}