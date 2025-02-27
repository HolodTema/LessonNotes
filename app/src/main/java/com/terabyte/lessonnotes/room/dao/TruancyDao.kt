package com.terabyte.lessonnotes.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Truancy

@Dao
interface TruancyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTruancy(truancy: Truancy)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateTruancy(truancy: Truancy)

    @Delete
    fun deleteTruancy(truancy: Truancy)

    @Query("SELECT * FROM truancies")
    fun getAllTruancies(): List<Truancy>

    @Query("SELECT * FROM truancies WHERE parentTermId = :parentTermId")
    fun getTruanciesByTermId(parentTermId: Long): List<Truancy>

}