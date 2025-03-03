package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Term

class CreateSubjectViewModel(application: Application): AndroidViewModel(application) {
    lateinit var term: Term

    val stateShowDialogColor = mutableStateOf(false)
    val stateSubjectName = mutableStateOf("")
    val stateColorIndex = mutableStateOf(0)

    val textCreate = application.resources.getString(R.string.create_subject_create)
    val textDone = application.resources.getString(R.string.create_subject_done)
    val textCreateNewSubject = application.resources.getString(R.string.create_subject_create_new_subject)
    val textSubjectColor = application.resources.getString(R.string.create_subject_subject_color)
    val textChangeColor = application.resources.getString(R.string.create_subject_change_color)
    val textChooseColor = application.resources.getString(R.string.create_subject_choose_color)
    val textSubjectName = application.resources.getString(R.string.create_subject_subject_name)

}