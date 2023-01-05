package com.example.myday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myday.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun GoToAddTaskActivity(view : View) {
        var i = Intent(this, AddTaskActivity::class.java)
        startActivity(i)
    }

    fun GoToSettingsActivity(view : View) {
        var i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }
}