package com.example.myday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myday.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddTaskBinding
    private var category: String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setCategoryDefault(view: View) {
        category = "1"
    }

    fun setCategoryCalendar(view: View) {
        category = "2"
    }

    fun setCategoryChill(view: View) {
        category = "3"
    }

    fun goToMainActivity(view : View) {
        val i = Intent()
        i.putExtra("title", binding.getTitle.text.toString())
        i.putExtra("time", binding.getTime.text.toString())
        i.putExtra("date", binding.getDate.text.toString())
        i.putExtra("category", category)
        i.putExtra("description", binding.getDescription.text.toString())
        setResult(RESULT_OK, i)
        finish()
    }

    fun closeAddTaskActivity(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

}