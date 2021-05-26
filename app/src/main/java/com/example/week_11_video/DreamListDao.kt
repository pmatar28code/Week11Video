package com.example.week_11_video

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.week_11_video.model.Item

@Dao
interface DreamListDao {
    @Insert
    fun add(item: Item)

    @Query("SELECT * FROM Item")
    fun getAll(): List<Item>
}