package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
import java.lang.Integer.min

class TermInfoViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term

    val stateSubjects = mutableStateOf(listOf<Subject>())
    val stateUrgentTasks = mutableStateOf(listOf<Task>())
    val stateDeleteConfirmDialog = mutableStateOf(false)

    //init fun when term is set
    fun onTermSet() {
        (application as MyApplication).roomManager.getSubjectsByTermId(term.id) { subjects ->
            stateSubjects.value = subjects
        }
        (application as MyApplication).roomManager.getTasksByTermId(term.id) { tasks ->
            val comparator = Comparator { task1: Task, task2: Task -> task2.importance - task1.importance }
            stateUrgentTasks.value = tasks.sortedWith(comparator).subList(0, min(5, tasks.size))
        }

    }

}