package com.terabyte.lessonnotes.room

import android.content.Context
import androidx.room.Room
import com.terabyte.lessonnotes.config.ROOM_DB_NAME
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RoomManager(context: Context) {

    private val database: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, ROOM_DB_NAME)
            .build()

    fun insertTerm(term: Term) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().insertTerm(term)
        }
    }

    fun updateTerm(term: Term) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().updateTerm(term)
        }
    }

    fun deleteTerm(term: Term) {
        CoroutineScope(Dispatchers.IO).launch {
            database.termDao().deleteTerm(term)
        }
    }

    fun deleteTermWithChildren(term: Term) {
        CoroutineScope(Dispatchers.IO).launch {
            val subjects = database.subjectDao().getSubjectsByTermId(term.id)
            val tasks = database.taskDao().getTasksByTermId(term.id)
            val truancies = database.truancyDao().getTruanciesByTermId(term.id)

            database.taskDao().deleteTaskList(tasks)
            database.subjectDao().deleteSubjectList(subjects)
            database.truancyDao().deleteTruancyList(truancies)
        }
    }

    fun getAllTerms(listener: (List<Term>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.termDao().getAllTerms()
            }
            listener(deferred.await())
        }
    }

    fun insertSubject(subject: Subject) {
        CoroutineScope(Dispatchers.IO).launch {
            database.subjectDao().insertSubject(subject)
        }
    }

    fun updateSubject(subject: Subject) {
        CoroutineScope(Dispatchers.IO).launch {
            database.subjectDao().updateSubject(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        CoroutineScope(Dispatchers.IO).launch {
            database.subjectDao().deleteSubject(subject)
        }
    }

    fun deleteSubjectWithChildren(subject: Subject) {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = database.taskDao().getTasksBySubjectId(subject.id)

            database.taskDao().deleteTaskList(tasks)
            database.subjectDao().deleteSubject(subject)
        }
    }

    fun getSubjectById(id: Long, listener: (Subject) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.subjectDao().getSubjectById(id)
            }
            listener(deferred.await()[0])
        }
    }

    fun getAllSubjects(listener: (List<Subject>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.subjectDao().getAllSubjects()
            }
            listener(deferred.await())
        }
    }

    fun getSubjectsByTermId(termId: Long, listener: (List<Subject>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.subjectDao().getSubjectsByTermId(termId)
            }
            listener(deferred.await())
        }
    }

    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            database.taskDao().insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            database.taskDao().updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            database.taskDao().deleteTask(task)
        }
    }

    fun getAllTasks(listener: (List<Task>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.taskDao().getAllTasks()
            }
            listener(deferred.await())
        }
    }

    fun getTasksBySubjectId(subjectId: Long, listener: (List<Task>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.taskDao().getTasksBySubjectId(subjectId)
            }
            listener(deferred.await())
        }
    }

    fun getTasksByTermId(termId: Long, listener: (List<Task>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.taskDao().getTasksByTermId(termId)
            }
            listener(deferred.await())
        }
    }

    fun insertTruancy(truancy: Truancy) {
        CoroutineScope(Dispatchers.IO).launch {
            database.truancyDao().insertTruancy(truancy)
        }
    }

    fun updateTruancy(truancy: Truancy) {
        CoroutineScope(Dispatchers.IO).launch {
            database.truancyDao().updateTruancy(truancy)
        }
    }

    fun deleteTruancy(truancy: Truancy) {
        CoroutineScope(Dispatchers.IO).launch {
            database.truancyDao().deleteTruancy(truancy)
        }
    }

    fun getAllTruancies(listener: (List<Truancy>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.truancyDao().getAllTruancies()
            }
            listener(deferred.await())
        }
    }

    fun getTruanciesByTermId(termId: Long, listener: (List<Truancy>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                database.truancyDao().getTruanciesByTermId(termId)
            }
            listener(deferred.await())
        }
    }

    fun getSubjectsForTasks(tasks: List<Task>, listener: (List<Pair<Task, Subject>>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                val result = arrayListOf<Pair<Task, Subject>>()

                tasks.forEach { task ->
                    val subject = database.subjectDao().getSubjectById(task.parentSubjectId)[0]
                    result.add(task to subject)
                }
                result
            }
            listener(deferred.await())
        }
    }

    fun getSubjectsForTruancies(truancies: List<Truancy>, listener: (List<Pair<Truancy, Subject>>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = async(Dispatchers.IO) {
                val result = arrayListOf<Pair<Truancy, Subject>>()

                truancies.forEach { truancy ->
                    val subject = database.subjectDao().getSubjectById(truancy.subjectId)[0]
                    result.add(truancy to subject)
                }
                result
            }
            listener(deferred.await())
        }
    }
}