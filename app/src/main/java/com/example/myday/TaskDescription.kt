package com.example.myday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myday.databinding.ActivityTaskDescriptionBinding

class TaskDescription : AppCompatActivity() {

    lateinit var binding : ActivityTaskDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var task = intent.getSerializableExtra("task_description") as Task
        binding.TitleTask.setText(task.title)
        binding.CategoryTask.setImageResource(task.category)
        if (task.time != "") binding.TimeTask.setText(task.time)
        if (task.date != "") binding.DateTask.setText(task.date)
        if (task.description != "") binding.DescriptionText.setText(task.description)

        binding.SaveEditButton.setOnClickListener {
            val myTask = Task (
            id = task.id,
            title = binding.TitleTask.text.toString(),
            category = task.category,
            time = binding.TimeTask.text.toString(),
            date = binding.DateTask.text.toString(),
            description = binding.DescriptionText.text.toString()
            )
            val i = Intent()
            i.putExtra("task_description_back", myTask)
            setResult(RESULT_OK, i)
            finish()
        }

        binding.CloseButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}