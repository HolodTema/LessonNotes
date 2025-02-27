package com.terabyte.lessonnotes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.terabyte.lessonnotes.config.ROOM_DB_VERSION
import com.terabyte.lessonnotes.room.dao.SubjectDao
import com.terabyte.lessonnotes.room.dao.TaskDao
import com.terabyte.lessonnotes.room.dao.TermDao
import com.terabyte.lessonnotes.room.dao.TruancyDao
import com.terabyte.lessonnotes.room.entity.Subject
import com.terabyte.lessonnotes.room.entity.Task
import com.terabyte.lessonnotes.room.entity.Term
import com.terabyte.lessonnotes.room.entity.Truancy

@Database(entities = [Term::class, Subject::class, Task::class, Truancy::class], version = ROOM_DB_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun termDao(): TermDao

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun truancyDao(): TruancyDao
}