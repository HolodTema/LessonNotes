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
    suspend fun insertTerm(term: Term)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTerm(term: Term)

    @Delete
    suspend fun deleteTerm(term: Term)

    @Query("SELECT * FROM terms")
    suspend fun getAllTerms(): List<Term>

}