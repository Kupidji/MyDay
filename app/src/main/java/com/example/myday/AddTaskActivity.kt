package com.example.myday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
    }

    fun GoToMainActivity(view : View) {
        finish()
    }
}