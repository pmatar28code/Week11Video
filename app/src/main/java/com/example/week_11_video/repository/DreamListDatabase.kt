package com.example.week_11_video.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.week_11_video.DreamListDao
import com.example.week_11_video.model.Item

@Database(
    entities =[Item::class],
    version = 1)
abstract class DreamListDatabase: RoomDatabase() {
    abstract fun dreamListDao():DreamListDao
}