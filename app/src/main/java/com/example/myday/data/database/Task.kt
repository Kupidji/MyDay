package com.example.myday.data.database

import android.app.PendingIntent
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: Int,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "time_in_long")
    var time_in_long : Long,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "notificationChannelID")
    var channelID : Int,
    )
    : java.io.Serializable