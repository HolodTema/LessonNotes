package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class CreateTermViewModel(application: Application): AndroidViewModel(application) {
    val stateTermNumber = mutableStateOf("")

    val stateStartYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
    val stateStartMonthIndex = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))

    val stateEndYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
    val stateEndMonthIndex = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))

    val stateTermDescription = mutableStateOf("")

    val stateShowDialogChangeStartDate = mutableStateOf(false)
    val stateShowDialogChangeEndDate = mutableStateOf(false)

    init {

    }

}