package com.terabyte.lessonnotes.application

import android.app.Application
import com.terabyte.lessonnotes.room.RoomManager

class MyApplication: Application() {
    lateinit var roomManager: RoomManager

    override fun onCreate() {
        super.onCreate()
        roomManager = RoomManager(this)
    }
}