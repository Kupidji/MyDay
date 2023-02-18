package com.example.myday.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myday.data.database.Task
import com.example.myday.databinding.TaskBoxPatternBinding

class TaskAdapter(val listener : RecyclerViewListener) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    var taskList = ArrayList<Task>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = TaskBoxPatternBinding.inflate(view, parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position], listener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTask(task : Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listOfTask : MutableList<Task>) {
        if (taskList.isEmpty()) {
            taskList.clear()
            taskList.addAll(listOfTask)
            notifyDataSetChanged()
        }
        else {
            taskList = listOfTask as ArrayList<Task>
        }
    }

    class TaskHolder (
        val binding : TaskBoxPatternBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task : Task, listener : RecyclerViewListener) {
            binding.TitlePattern.text = task.title
            if (task.time.isNotEmpty()) {
                binding.TimePattern.visibility = View.VISIBLE
                binding.TimePattern.text = task.time
            }
            else binding.TimePattern.visibility = View.GONE
            binding.TaskBoxCategoryPattern.setImageResource(task.category)

            binding.checkBox.setOnClickListener {
                listener.onClickCheckBox(task)
            }

            binding.TaskBoxPattern.setOnClickListener {
                listener.onClickTaskBoxPattern(task)
            }
        }
    }

    interface RecyclerViewListener {
        fun onClickCheckBox(task : Task)
        fun onClickTaskBoxPattern(task : Task)
    }

}