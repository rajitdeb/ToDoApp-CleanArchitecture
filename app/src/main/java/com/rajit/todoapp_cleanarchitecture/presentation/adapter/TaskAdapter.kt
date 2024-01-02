package com.rajit.todoapp_cleanarchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rajit.todoapp_cleanarchitecture.R
import com.rajit.todoapp_cleanarchitecture.databinding.TaskItemviewBinding
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.util.MyDiffUtil

class TaskAdapter(
    private val onPinClick: (Task) -> Unit,
    private val deleteTask: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList: List<Task> = mutableListOf()

    inner class TaskViewHolder(val binding: TaskItemviewBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemviewBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.binding.taskTitleTV.text = currentTask.title

        val pinStateDrawable = when(currentTask.isPinned) {
            true -> R.drawable.ic_unpin
            false -> R.drawable.ic_pin
        }

        holder.binding.taskPinIV.setImageResource(pinStateDrawable)

        holder.binding.taskPinIV.setOnClickListener { onPinClick(currentTask) }

        holder.binding.deleteTaskBtn.setOnClickListener { deleteTask(currentTask) }

    }

    fun submitList(newList: List<Task>) {
        val diffCallback = MyDiffUtil(taskList, newList)
        val result = DiffUtil.calculateDiff(diffCallback)
        taskList = newList
        result.dispatchUpdatesTo(this)
    }

}