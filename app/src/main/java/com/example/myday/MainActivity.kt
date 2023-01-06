package com.example.myday

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myday.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var launcher : ActivityResultLauncher<Intent>? = null
    lateinit var binding : ActivityMainBinding
    val adapter = TaskAdapter()
    var currentDate: String = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.CurrentDate.text = currentDate
        createTask()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                adapter.addTask(result.data?.getSerializableExtra("task") as Task)
            }
        }

    }

    fun GoToSettingsActivity(view : View) {
        var i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

    private fun createTask() {
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.adapter = adapter
        binding.AddTaskButton.setOnClickListener {
            launcher?.launch(Intent(this@MainActivity, AddTaskActivity::class.java))
        }
    }
}