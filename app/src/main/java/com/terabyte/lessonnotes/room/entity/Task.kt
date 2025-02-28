package com.terabyte.lessonnotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val parentSubjectId: Long = 0,
    val parentTermId: Long = 0,
    val name: String,
    val description: String,
    val date: String,
    val importance: Int = 0,
    val completed: Boolean = false,
)
