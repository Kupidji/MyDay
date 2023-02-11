package com.example.myday.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    fun insertTask(task : Task)
    @Query ("SELECT * FROM tasks")
    fun getAllTasks() : LiveData <List<Task>>
    @Delete
    fun deleteTask(task : Task)
    @Update
    fun editTask(task : Task)
}