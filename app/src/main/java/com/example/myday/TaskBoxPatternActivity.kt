package com.example.myday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myday.databinding.TaskBoxPatternBinding

class TaskBoxPatternActivity : AppCompatActivity() {

    lateinit var binding : TaskBoxPatternBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskBoxPatternBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.TaskBoxPattern.setOnClickListener {

        }
    }

}