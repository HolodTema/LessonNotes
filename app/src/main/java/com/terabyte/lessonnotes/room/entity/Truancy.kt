package com.terabyte.lessonnotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "truancies")
data class Truancy (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val parentTermId: Long,
    val subjectId: Long,
    val reason: String,
    val date: String
)