package com.example.myday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myday.databinding.ActivityTaskDescriptionBinding

class TaskDescription : AppCompatActivity() {

    lateinit var binding : ActivityTaskDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}