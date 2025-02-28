package com.terabyte.lessonnotes.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(task: Task)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE parentSubjectId = :parentSubjectId")
    fun getTasksBySubjectId(parentSubjectId: Long): List<Task>

    @Query("SELECT * FROM tasks WHERE parentTermId = :parentTermId")
    fun getTasksByTermId(parentTermId: Long): List<Task>
}