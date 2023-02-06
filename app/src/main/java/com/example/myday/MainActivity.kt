
//███╗░░░███╗██╗░░░██╗██████╗░░█████╗░██╗░░░██╗
//████╗░████║╚██╗░██╔╝██╔══██╗██╔══██╗╚██╗░██╔╝
//██╔████╔██║░╚████╔╝░██║░░██║███████║░╚████╔╝░
//██║╚██╔╝██║░░╚██╔╝░░██║░░██║██╔══██║░░╚██╔╝░░
//██║░╚═╝░██║░░░██║░░░██████╔╝██║░░██║░░░██║░░░
//╚═╝░░░░░╚═╝░░░╚═╝░░░╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░

package com.example.myday

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.myday.data.Task
import com.example.myday.data.database.TaskDB
import com.example.myday.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService

class MainActivity : AppCompatActivity(), TaskAdapter.RecyclerViewListener {

    private var launcher : ActivityResultLauncher<Intent>? = null
    private var launcher2 : ActivityResultLauncher<Intent>? = null
    lateinit var binding : ActivityMainBinding
    private var adapter = TaskAdapter(this)
    private lateinit var DB : TaskDB
    lateinit var taskViewModel: TaskViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        DB = TaskDB.getDB(this)

        binding.CurrentDate.text = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())

        // отрисовываю RecyclerView
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = adapter
        taskViewModel.getAllTasks().observe(this) { taskList ->
            adapter.taskList.clear() // перенести в TaskAdapter
            taskList.forEach {
                adapter.addTask(it)
            }
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
                taskViewModel.addTask(result.data?.getSerializableExtra("task") as Task)
            }
        }

        // Редактирую задачу
        launcher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                taskViewModel.editTask(result.data?.getSerializableExtra("task_description_back") as Task)
            }
        }
    }

     override fun onClickCheckBox(task: Task) {
         taskViewModel.deleteTask(task)
     }

     override fun onClickTaskBoxPattern(task: Task) {
         //Редактирую задачу
         val i = Intent(this, TaskDescription::class.java)
         i.putExtra("task_description", task)
         launcher2?.launch(i)
     }

 }