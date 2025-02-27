package com.terabyte.lessonnotes.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.terabyte.lessonnotes.room.entity.Term

@Dao
interface TermDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTerm(term: Term)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateTerm(term: Term)

    @Delete
    fun deleteTerm(term: Term)

    @Query("SELECT * FROM terms")
    fun getAllTerms(): List<Term>

}