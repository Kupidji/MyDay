package com.example.myday.view

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myday.data.database.Task
import com.example.myday.databinding.ActivityTaskEditBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskEdit : AppCompatActivity() {

    lateinit var binding : ActivityTaskEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var task = intent.getSerializableExtra("task_description") as Task
        binding.TitleTask.setText(task.title)
        binding.CategoryTask.setImageResource(task.category)
        if (task.time != "") binding.TimeTask.setText(task.time)
        if (task.description != "") binding.DescriptionText.setText(task.description)

        binding.SaveEditButton.setOnClickListener {
            val myTask = Task (
            id = task.id,
            title = binding.TitleTask.text.toString(),
            category = task.category,
            time = binding.TimeTask.text.toString(),
            description = binding.DescriptionText.text.toString(),
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

        getTime(binding.TimeTask, this)

    }

    fun getTime(textView: TextView, context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        binding.clockBtnn.setOnClickListener {
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true,
            ).show()
        }

        binding.TimeTask.setOnClickListener {
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true,
            ).show()
        }
    }

}