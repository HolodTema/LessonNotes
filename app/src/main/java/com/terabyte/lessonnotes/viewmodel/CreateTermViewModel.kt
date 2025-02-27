package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class CreateTermViewModel(application: Application): AndroidViewModel(application) {
    val stateTermNumber = mutableStateOf("")
    val stateTermDateStart = mutableStateOf("")
    val stateTermDateEnd = mutableStateOf("")
    val stateTermDescription = mutableStateOf("")

    val stateShowDialogChangeStartDate = mutableStateOf(false)
    val stateShowDialogChangeEndDate = mutableStateOf(false)

    init {

    }

}