package com.example.myday.view

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myday.R
import com.example.myday.data.database.Task
import com.example.myday.databinding.ActivityAddTaskBinding
import com.example.myday.service.AlarmService
import com.example.myday.util.Constants
import com.example.myday.util.RandomUtil
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding
    private var category: Int = R.drawable.default_category
    private var doubleBackToExitPressedOnce = false
    private lateinit var alarmService: AlarmService
    private var ourTime = 0L
    private var ourMassage = ""
    private var channelID = 0

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmService = AlarmService(this)

        binding.defaultCategory.setOnClickListener {
            setCategoryDefault()
        }

        binding.calendarCategory.setOnClickListener {
            setCategoryCalendar()
        }

        binding.chillCategory.setOnClickListener {
            setCategoryChill()
        }

        binding.goToMainActivity.setOnClickListener {
            closeAddTaskActivity()
        }

        binding.clockBtn.setOnClickListener {
            setAlarm(binding.getTime) { ourTime = it }
        }

        binding.getTime.setOnClickListener {
            setAlarm(binding.getTime) { ourTime = it }
        }

        binding.ButtonSave.setOnClickListener {
            goToMainActivity()
            if (ourTime != 0L) alarmService.setExactAlarm(ourTime, binding.getTitle.text.toString(), channelID)
        }


    }

    override fun onBackPressed() {
        if (binding.getTitle.text.isNotBlank()){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, R.string.Go_back, Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
        else {
            super.onBackPressed()
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun goToMainActivity() {
        if (binding.getTitle.text.isEmpty()) {
            binding.getTitle.error = "Обязательное поле"
        }
        else {
            val task = Task(
                id = null,
                title = binding.getTitle.text.toString(),
                category = category,
                time = binding.getTime.text.toString(),
                time_in_long = ourTime,
                description = binding.getDescription.text.toString(),
                channelID = 0
            )
            if (task.time.isNotBlank()) {
                task.channelID = abs(RandomUtil.getRandomInt())
                channelID = task.channelID
            }
            ourMassage = task.title
            val i = Intent()
            i.putExtra(Constants.ADD_TASK, task)
            setResult(RESULT_OK, i)
            finish()
        }
    }

    private fun closeAddTaskActivity() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun setCategoryDefault() {
        binding.defaultCategory.setImageResource(R.drawable.choosen_default_category)
        binding.calendarCategory.setImageResource(R.drawable.calendar_category)
        binding.chillCategory.setImageResource(R.drawable.chill_category)
        category = R.drawable.default_category
    }

    private fun setCategoryCalendar() {
        binding.calendarCategory.setImageResource(R.drawable.choosen_calendar_category)
        binding.defaultCategory.setImageResource(R.drawable.default_category)
        binding.chillCategory.setImageResource(R.drawable.chill_category)
        category = R.drawable.calendar_category
    }

    private fun setCategoryChill() {
        binding.chillCategory.setImageResource(R.drawable.choosen_chill_category)
        binding.defaultCategory.setImageResource(R.drawable.default_category)
        binding.calendarCategory.setImageResource(R.drawable.calendar_category)
        category = R.drawable.chill_category
    }

    private fun setAlarm(textView: TextView, callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            TimePickerDialog(
                this@AddTaskActivity,
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