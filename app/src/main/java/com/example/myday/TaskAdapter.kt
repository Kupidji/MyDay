package com.example.myday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myday.databinding.TaskBoxPatternBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    val taskList = ArrayList<Task>()

    class TaskHolder(item : View) : RecyclerView.ViewHolder(item) {

        val binding = TaskBoxPatternBinding.bind(item)

        fun bind(task : Task) {
            binding.TitlePattern.text = task.title
            binding.TimePattern.text = task.time
            binding.TaskBoxCategoryPattern.setImageResource(task.category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_box_pattern, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun addTask(task : Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

}