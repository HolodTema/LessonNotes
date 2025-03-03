package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
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


    val textCreateNewTask = application.resources.getString(R.string.create_task_create_new_task)
    val textTaskName = application.resources.getString(R.string.create_task_name)
    val textTaskDescription = application.resources.getString(R.string.create_task_description)
    val textChangeDate = application.resources.getString(R.string.create_task_change_date)
    val textDone = application.resources.getString(R.string.create_task_done)
    val textChooseImportance = application.resources.getString(R.string.create_task_choose_importance)
    val textNotImportant = application.resources.getString(R.string.create_task_not_important)
    val textLittleImportant = application.resources.getString(R.string.create_task_little_important)
    val textImportant = application.resources.getString(R.string.create_task_important)
    val textVeryImportant = application.resources.getString(R.string.create_task_very_important)
    val textASAP = application.resources.getString(R.string.create_task_asap)
    val textCreate = application.resources.getString(R.string.create_task_create)
}