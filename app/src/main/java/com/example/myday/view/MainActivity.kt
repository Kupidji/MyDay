
//███╗░░░███╗██╗░░░██╗██████╗░░█████╗░██╗░░░██╗
//████╗░████║╚██╗░██╔╝██╔══██╗██╔══██╗╚██╗░██╔╝
//██╔████╔██║░╚████╔╝░██║░░██║███████║░╚████╔╝░
//██║╚██╔╝██║░░╚██╔╝░░██║░░██║██╔══██║░░╚██╔╝░░
//██║░╚═╝░██║░░░██║░░░██████╔╝██║░░██║░░░██║░░░
//╚═╝░░░░░╚═╝░░░╚═╝░░░╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░

package com.example.myday.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myday.data.database.Task
import com.example.myday.data.database.TaskDB
import com.example.myday.databinding.ActivityMainBinding
import com.example.myday.service.AlarmService
import com.example.myday.util.Constants
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), TaskAdapter.RecyclerViewListener {

    private var launcher : ActivityResultLauncher<Intent>? = null
    private var launcher2 : ActivityResultLauncher<Intent>? = null
    private lateinit var binding : ActivityMainBinding
    private var adapter = TaskAdapter(this)
    private lateinit var DB : TaskDB
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var alarmService: AlarmService
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // последняя выбранная тема
        settings = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE)
        if (settings.contains(Constants.CURRENT_THEME)) {
            AppCompatDelegate.setDefaultNightMode(settings.getInt(Constants.CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_YES))
        }

        alarmService = AlarmService(this)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        DB = TaskDB.getDB(this)

        binding.CurrentDate.text = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())

        checkCountOfTasks()

        // отрисовываю RecyclerView
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = adapter
        taskViewModel.getAllTasks().observe(this) { taskList ->
            adapter.taskList.clear()
            adapter.setData(taskList as MutableList<Task>)
        }

        binding.SettingsButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }

        binding.AddTaskButton.setOnClickListener {
            launcher?.launch(Intent(this@MainActivity, AddTaskActivity::class.java))
        }

        // Добавляю задачу
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                taskViewModel.addTask(result.data?.getSerializableExtra(Constants.ADD_TASK) as Task)
            }
            checkCountOfTasks()
        }

        // Редактирую задачу
        launcher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                taskViewModel.editTask(result.data?.getSerializableExtra(Constants.EDIT_TASK_OUTPUT) as Task)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val editor = settings.edit()
        editor.putInt(Constants.CURRENT_THEME, AppCompatDelegate.getDefaultNightMode()).apply()
    }

     override fun onClickCheckBox(task: Task) {
         alarmService.cancelNotification(task.time_in_long, task.title, task.channelID)
         taskViewModel.deleteTask(task)
         checkCountOfTasks()
     }

     override fun onClickTaskBoxPattern(task: Task) {
         val i = Intent(this, TaskEdit::class.java)
         i.putExtra(Constants.EDIT_TASK_INPUT, task)
         launcher2?.launch(i)
     }

    fun checkCountOfTasks() {
        taskViewModel.getAllTasks().observe(this) { taskList ->
            if (taskList.isEmpty()) binding.NothingThere.visibility = View.VISIBLE
            else binding.NothingThere.visibility = View.GONE
        }
    }

 }
