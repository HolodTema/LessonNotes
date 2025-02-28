package com.terabyte.lessonnotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "terms")
data class Term(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: Int,
    val period: String,
    val description: String
): Serializable