package com.example.myday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myday.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddTaskBinding
    private var category: Int = R.drawable.default_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setCategoryDefault(view: View) {
        category = R.drawable.default_category
    }

    fun setCategoryCalendar(view: View) {
        category = R.drawable.calendar_category
    }

    fun setCategoryChill(view: View) {
        category = R.drawable.chill_category
    }

    fun goToMainActivity(view : View) {
        val task = Task(id = null, title = binding.getTitle.text.toString(), category = category,time = binding.getTime.text.toString(), date = binding.getDate.text.toString(), description = binding.getDescription.text.toString())
        val i = Intent()
        i.putExtra("task", task)
        setResult(RESULT_OK, i)
        finish()
    }

    fun closeAddTaskActivity(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

}