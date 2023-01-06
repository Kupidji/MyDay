package com.example.myday

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myday.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var launcher : ActivityResultLauncher<Intent>? = null
    lateinit var binding : ActivityMainBinding

    private var title : String? = null
    private var time : String? = null
    private var date : String? = null
    private var category : String? = null
    private var description : String? = null
    //private var currentDate : Date = Calendar.getInstance().time
    var currentDate: String = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.CurrentDate.text = currentDate
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if (result.resultCode == RESULT_OK) {

                title = result.data?.getStringExtra("title")
                time = result.data?.getStringExtra("time")
                date = result.data?.getStringExtra("date")
                category = result.data?.getStringExtra("category")
                description = result.data?.getStringExtra("description")

                binding.ExampleTitle.text = title

                if (time.toString().isEmpty()) { binding.ExampleTime.visibility = View.GONE }
                else {
                    binding.ExampleTime.visibility = View.VISIBLE
                    binding.ExampleTime.text = time
                }

                if (category == null) binding.ExampleTaskBoxCategory.setImageResource(R.drawable.default_category)
                else if (category == "1") binding.ExampleTaskBoxCategory.setImageResource(R.drawable.default_category)
                else if (category == "2") binding.ExampleTaskBoxCategory.setImageResource(R.drawable.calendar_category)
                else if (category == "3") binding.ExampleTaskBoxCategory.setImageResource(R.drawable.chill_category)
            }
        }

    }

    fun GoToAddTaskActivity(view : View) {
        launcher?.launch(Intent(this, AddTaskActivity::class.java))
    }

    fun GoToSettingsActivity(view : View) {
        var i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
    }

}