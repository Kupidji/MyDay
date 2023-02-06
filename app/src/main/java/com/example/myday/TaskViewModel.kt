package com.example.myday

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myday.data.Task
import com.example.myday.data.database.Dao
import com.example.myday.data.database.TaskDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private var taskDao : Dao
    private var executorService : ExecutorService

    init {
        taskDao = TaskDB.getDB(application).getDao()
        executorService = Executors.newSingleThreadExecutor()
    }

    fun addTask(task : Task) {
        executorService.execute {taskDao.insertTask(task)}
    }

    fun deleteTask(task : Task) {
        executorService.execute { taskDao.deleteTask(task) }
    }

    fun editTask(task: Task) {
        executorService.execute { taskDao.editTask(task) }
    }

    fun getAllTasks() : LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

}