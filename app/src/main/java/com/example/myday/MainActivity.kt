package com.example.myday

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myday.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

 class MainActivity : AppCompatActivity(), TaskAdapter.RecyclerViewListener {

    private var launcher : ActivityResultLauncher<Intent>? = null
    private var launcher2 : ActivityResultLauncher<Intent>? = null
    lateinit var binding : ActivityMainBinding
    private var adapter = TaskAdapter(this)
    private lateinit var DB : TaskDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DB = TaskDB.getDB(this)

        binding.CurrentDate.text = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())

        // Добавляю задачу
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                Thread{
                    DB.getDao().insertTask(result.data?.getSerializableExtra("task") as Task)
                }.start()
                if (adapter.itemCount == 0) binding.NothingThere.visibility = View.VISIBLE
                else binding.NothingThere.visibility = View.GONE // не работает
                recreate()
            }
        }

        launcher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                Thread{
                    DB.getDao().editTask(result.data?.getSerializableExtra("task_description_back") as Task)
                }.start()
                recreate()
            }
        }

        // отрисовываю RecyclerView
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = adapter
        DB.getDao().getAllTasks().asLiveData().observe(this){
            it.forEach {
                if (!adapter.taskList.contains(it)) adapter.addTask(it)
            }
        }
        if (adapter.itemCount == 0) binding.NothingThere.visibility = View.VISIBLE
        else binding.NothingThere.visibility = View.GONE // не работает

        binding.SettingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.AddTaskButton.setOnClickListener {
            launcher?.launch(Intent(this@MainActivity, AddTaskActivity::class.java))
        }
    }

     override fun onClickCheckBox(task: Task) {
         Thread {
             if (adapter.taskList.contains(task)) {
                 adapter.delTask(task)
                 DB.getDao().deleteTask(task)
             }
         }.start()
         if (adapter.itemCount == 0) binding.NothingThere.visibility = View.VISIBLE
         else binding.NothingThere.visibility = View.GONE // не работает
         recreate()
     }

     override fun onClickTaskBoxPattern(task: Task) {
         //Редактирую задачу
         val i = Intent(this, TaskDescription::class.java)
         i.putExtra("task_description", task)
         launcher2?.launch(i)
     }
 }