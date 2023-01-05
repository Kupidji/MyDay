package com.example.myday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myday.databinding.ActivityAddTaskBinding
import com.example.myday.databinding.ActivityMainBinding

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun GoToMainActivity(view : View) {
        finish()
    }
}