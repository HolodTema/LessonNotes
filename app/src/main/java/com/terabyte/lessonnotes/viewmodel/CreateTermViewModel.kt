package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R

class CreateTermViewModel(application: Application): AndroidViewModel(application) {
    val stateTermNumber = mutableStateOf("")

    val stateStartYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
    val stateStartMonthIndex = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))

    val stateEndYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
    val stateEndMonthIndex = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))

    val stateTermDescription = mutableStateOf("")

    val stateShowDialogChangeStartDate = mutableStateOf(false)
    val stateShowDialogChangeEndDate = mutableStateOf(false)

    val months = application.resources.getStringArray(R.array.months)

    val textCreateNewTerm = application.resources.getString(R.string.create_term_create_new_term)
    val textTermNumber = application.resources.getString(R.string.create_term_term_number)
    val textStartsAt = application.resources.getString(R.string.create_term_starts_at)
    val textEndsAt = application.resources.getString(R.string.create_term_ends_at)
    val textChange = application.resources.getString(R.string.create_term_change)
    val textTermDescription = application.resources.getString(R.string.create_term_description)
    val textCreate = application.resources.getString(R.string.create_term_create)
    val textDone = application.resources.getString(R.string.create_term_done)

}