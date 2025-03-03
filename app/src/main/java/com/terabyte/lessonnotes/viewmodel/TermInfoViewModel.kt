package com.terabyte.lessonnotes.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.terabyte.lessonnotes.R
import com.terabyte.lessonnotes.application.MyApplication
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
import java.lang.Integer.min

class TermInfoViewModel(private val application: Application): AndroidViewModel(application) {
    lateinit var term: Term

    val stateSubjects = mutableStateOf(listOf<Subject>())
    val stateUrgentTasks = mutableStateOf(listOf<Pair<Task, Subject>>())
    val stateDeleteConfirmDialog = mutableStateOf(false)

    val textTerm = application.resources.getString(R.string.term)
    val textUrgentTasks = application.resources.getString(R.string.term_info_urgent_tasks)
    val textSubjects = application.resources.getString(R.string.term_info_subjects)
    val textViewTruancies = application.resources.getString(R.string.term_info_view_truancies)
    val textDialogDeleteTitle = application.resources.getString(R.string.term_info_dialog_delete_title)
    val textDialogDeleteDesc = application.resources.getString(R.string.term_info_dialog_delete_desc)
    val textDelete = application.resources.getString(R.string.term_info_delete)

    //init fun when term is set
    fun onTermSet() {
        (application as MyApplication).roomManager.getSubjectsByTermId(term.id) { subjects ->
            stateSubjects.value = subjects
        }


        application.roomManager.getTasksByTermId(term.id) { tasks ->
            val comparator = Comparator { task1: Task, task2: Task -> task2.importance - task1.importance }

            val urgentTasks = tasks.sortedWith(comparator).subList(0, min(5, tasks.size))

            application.roomManager.getSubjectsForTasks(urgentTasks) { urgentTasksPairs ->
                stateUrgentTasks.value = urgentTasksPairs
            }
        }
    }

}