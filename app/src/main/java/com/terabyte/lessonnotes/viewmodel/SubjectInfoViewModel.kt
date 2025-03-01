package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term

class SubjectInfoViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term
    lateinit var subject: Subject

    val stateTabIndex = mutableStateOf(0)
    val stateTasks = mutableStateOf(listOf<Task>())
    val stateIncompleteTasks = mutableStateOf(listOf<Task>())
    val stateCompleteTasks = mutableStateOf(listOf<Task>())
    val stateShowDeleteDialog = mutableStateOf(false)

    fun onExtrasGot() {
        (application as MyApplication).roomManager.getTasksBySubjectId(subject.id) {
            val comparator = Comparator { task1: Task, task2: Task ->
                task1.importance - task2.importance
            }

            stateTasks.value = it
            stateIncompleteTasks.value = it.filter { task ->
                !task.completed
            }.sortedWith(comparator)
            stateCompleteTasks.value = it.filter { task ->
                task.completed
            }.sortedWith(comparator)
        }
    }

}