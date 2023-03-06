package com.example.myday.view

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myday.data.database.Task
import com.example.myday.databinding.ActivityTaskEditBinding
import com.example.myday.service.AlarmService
import com.example.myday.util.Constants
import com.example.myday.util.RandomUtil
import java.text.SimpleDateFormat
import java.util.*

class TaskEdit : AppCompatActivity() {

    lateinit var binding : ActivityTaskEditBinding
    var ourTime = 0L
    private lateinit var alarmService: AlarmService
    private var channelID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmService = AlarmService(this)

        var task = intent.getSerializableExtra(Constants.EDIT_TASK_INPUT) as Task
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
            time_in_long = ourTime,
            description = binding.DescriptionText.text.toString(),
            channelID = 0
            )

            if (myTask.time.isNotBlank()) {
                if (task.time != myTask.time) {
                    alarmService.cancelNotification(task.time_in_long, task.title, task.channelID)
                }
                myTask.channelID = RandomUtil.getRandomInt()
                channelID = myTask.channelID
            }

            val i = Intent()
            i.putExtra(Constants.EDIT_TASK_OUTPUT, myTask)
            setResult(RESULT_OK, i)

            if (ourTime != 0L) alarmService.setExactAlarm(ourTime, binding.TitleTask.text.toString(), myTask.channelID)

            finish()
        }

        binding.CloseButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.clockBtnn.setOnClickListener {
            setAlarm(binding.TimeTask) {ourTime = it}
        }

        binding.TimeTask.setOnClickListener {
            setAlarm(binding.TimeTask) {ourTime = it}
        }

    }

    private fun setAlarm(textView: TextView, callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            TimePickerDialog(
                this@TaskEdit,
                0,
                { _, hour, minute ->
                    this.set(Calendar.HOUR_OF_DAY, hour)
                    this.set(Calendar.MINUTE, minute)
                    callback(this.timeInMillis)
                    textView.text = SimpleDateFormat("HH:mm").format(this.time)
                },
                this.get(Calendar.HOUR_OF_DAY),
                this.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

}