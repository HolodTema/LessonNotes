package com.terabyte.lessonnotes.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.terabyte.lessonnotes.room.entity.Subject

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubject(subject: Subject)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateSubject(subject: Subject)

    @Delete
    fun deleteSubject(subject: Subject)

    @Delete
    fun deleteSubjectList(subjects: List<Subject>)

    @Query("SELECT * FROM subjects WHERE id = :id")
    fun getSubjectById(id: Long): List<Subject>

    @Query("SELECT * FROM subjects")
    fun getAllSubjects(): List<Subject>

    @Query("SELECT * FROM subjects WHERE parentTermId = :parentTermId")
    fun getSubjectsByTermId(parentTermId: Long): List<Subject>

}