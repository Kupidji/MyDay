package com.example.myday

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myday.databinding.ActivityAddTaskBinding


class AddTaskActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddTaskBinding
    private var category: Int = R.drawable.default_category
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (binding.getTitle.text.isNotBlank()){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Нажмите кнопку назад для выхода", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
        else {
            super.onBackPressed()
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun setCategoryDefault(view: View) {
        binding.defaultCategory.setImageResource(R.drawable.choosen_default_category)
        binding.calendarCategory.setImageResource(R.drawable.calendar_category)
        binding.chillCategory.setImageResource(R.drawable.chill_category)
        category = R.drawable.default_category
    }

    fun setCategoryCalendar(view: View) {
        binding.calendarCategory.setImageResource(R.drawable.choosen_calendar_category)
        binding.defaultCategory.setImageResource(R.drawable.default_category)
        binding.chillCategory.setImageResource(R.drawable.chill_category)
        category = R.drawable.calendar_category
    }

    fun setCategoryChill(view: View) {
        binding.chillCategory.setImageResource(R.drawable.choosen_chill_category)
        binding.defaultCategory.setImageResource(R.drawable.default_category)
        binding.calendarCategory.setImageResource(R.drawable.calendar_category)
        category = R.drawable.chill_category
    }

    fun goToMainActivity(view : View) {
        if (binding.getTitle.text.isEmpty()) {
            binding.getTitle.error = "Обязательное поле"
        }
        else {
            val task = Task(
                id = null,
                title = binding.getTitle.text.toString(),
                category = category,
                time = binding.getTime.text.toString(),
                date = binding.getDate.text.toString(),
                description = binding.getDescription.text.toString()
            )
            val i = Intent()
            i.putExtra("task", task)
            setResult(RESULT_OK, i)
            finish()
        }
    }

    fun closeAddTaskActivity(view: View) {
        if (binding.getTitle.text.isBlank()) {

        }
        setResult(RESULT_CANCELED)
        finish()
    }
}