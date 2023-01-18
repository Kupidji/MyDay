package com.example.myday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "category")
    val category : Int,
    @ColumnInfo(name = "time")
    var time : String,
    @ColumnInfo(name = "date")
    val date : String,
    @ColumnInfo(name = "description")
    val description : String
    )
    : java.io.Serializable