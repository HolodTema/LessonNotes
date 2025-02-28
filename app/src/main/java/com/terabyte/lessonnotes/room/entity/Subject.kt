package com.terabyte.lessonnotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val parentTermId: Long = 0,
    val name: String,
    val colorType: Int = 0
)
