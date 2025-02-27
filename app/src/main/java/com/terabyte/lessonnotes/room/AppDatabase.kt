package com.terabyte.lessonnotes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.terabyte.lessonnotes.config.ROOM_DB_VERSION
import com.terabyte.lessonnotes.room.dao.TermDao
import com.terabyte.lessonnotes.room.entity.Term

@Database(entities = [Term::class], version = ROOM_DB_VERSION)
abstract class AppDatabase: RoomDatabase() {
    abstract fun termDao(): TermDao
}