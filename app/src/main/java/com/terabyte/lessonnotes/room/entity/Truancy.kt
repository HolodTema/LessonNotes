package com.terabyte.lessonnotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "truancies")
data class Truancy (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val parentTermId: Long = 0,
    val subjectId: Long = 0,
    val reason: String,
    val date: String
)