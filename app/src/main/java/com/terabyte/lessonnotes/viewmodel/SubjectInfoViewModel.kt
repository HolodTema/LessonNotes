package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
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

    val textDelete = application.resources.getString(R.string.subject_info_delete)
    val textComplete = application.resources.getString(R.string.subject_info_complete)
    val textIncomplete = application.resources.getString(R.string.subject_info_incomplete)
    val textDialogDeleteTitle = application.resources.getString(R.string.subject_info_dialog_delete_title)
    val textDialogDeleteDesc = application.resources.getString(R.string.subject_info_dialog_delete_desc)

    fun onExtrasGot() {
        (application as MyApplication).roomManager.getTasksBySubjectId(subject.id) {
            val comparator = Comparator { task1: Task, task2: Task ->
                task2.importance - task1.importance
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

    fun makeTaskIncomplete(task: Task) {
        val updatedTask = task.copy(completed = false)

        val comparator = Comparator { task1: Task, task2: Task ->
            task2.importance - task1.importance
        }

        stateTasks.value = stateTasks.value.map { t ->
            if (t.id == updatedTask.id) {
                updatedTask
            }
            else {
                t
            }
        }
        stateIncompleteTasks.value = stateTasks.value.filter { t ->
            !t.completed
        }.sortedWith(comparator)
        stateCompleteTasks.value = stateTasks.value.filter { t ->
            t.completed
        }.sortedWith(comparator)

        (application as MyApplication).roomManager.updateTask(updatedTask)
    }

    fun makeTaskComplete(task: Task) {
        val updatedTask = task.copy(completed = true)

        val comparator = Comparator { task1: Task, task2: Task ->
            task2.importance - task1.importance
        }

        stateTasks.value = stateTasks.value.map { t ->
            if (t.id == updatedTask.id) {
                updatedTask
            }
            else {
                t
            }
        }
        stateIncompleteTasks.value = stateTasks.value.filter { t ->
            !t.completed
        }.sortedWith(comparator)
        stateCompleteTasks.value = stateTasks.value.filter { t ->
            t.completed
        }.sortedWith(comparator)

        (application as MyApplication).roomManager.updateTask(updatedTask)
    }

}