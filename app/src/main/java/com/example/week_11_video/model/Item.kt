package com.example.week_11_video.model

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item")
data class Item(
    @PrimaryKey(autoGenerate = true) val key:Int = 0,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "price") val price:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "store") val store:String,
    @ColumnInfo(name = "image") val image: String
)
