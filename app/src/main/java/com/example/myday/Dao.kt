package com.example.myday

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Insert
    fun insertTask(task : Task)
    @Query ("SELECT * FROM tasks")
    fun getAllTasks() : Flow <List<Task>>
    @Query ("SELECT * FROM tasks where id = :taskID")
    fun getTask(taskID : Int) : Flow <List<Task>>
    @Delete
    fun deleteTask(task : Task)
    @Update
    fun editTask(task : Task)
}