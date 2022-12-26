package com.example.myday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun GoToAddTaskActivity(view : View) {
        var i = Intent(this, AddTaskActivity::class.java)
        startActivity(i)
    }
}