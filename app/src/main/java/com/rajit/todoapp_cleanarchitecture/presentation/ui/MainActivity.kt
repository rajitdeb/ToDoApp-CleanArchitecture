package com.rajit.todoapp_cleanarchitecture.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajit.todoapp_cleanarchitecture.data.local.database.AppDatabase
import com.rajit.todoapp_cleanarchitecture.data.local.repository.RoomTaskRepository
import com.rajit.todoapp_cleanarchitecture.databinding.ActivityMainBinding
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.domain.usecase.AddTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.GetTasksUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.PinTaskUseCase
import com.rajit.todoapp_cleanarchitecture.presentation.adapter.TaskAdapter
import com.rajit.todoapp_cleanarchitecture.presentation.presenter.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var mAdapter: TaskAdapter
    private lateinit var db: AppDatabase
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        db = AppDatabase.invoke(applicationContext)

        val taskDao = db.taskDao()

        val roomTaskRepository =RoomTaskRepository(taskDao)

        taskViewModel = TaskViewModel(
            GetTasksUseCase(roomTaskRepository),
            AddTaskUseCase(roomTaskRepository),
            PinTaskUseCase(roomTaskRepository),
            DeleteTaskUseCase(roomTaskRepository)
        )

        mAdapter = TaskAdapter(
            onPinClick = { onPinClicked(it) },
            deleteTask = { deleteTask(it) }
        )

        _binding.taskRV.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        loadAllTasks()

        taskViewModel.tasks.observe(this) {
            mAdapter.submitList(it)
        }

        _binding.addNewTaskBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNewTaskActivity::class.java))
        }

    }

    private fun loadAllTasks() {
        taskViewModel.getAllTasks()
    }

    private fun onPinClicked(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskViewModel.pinTask(task, !task.isPinned)
        }
    }

    private fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskViewModel.deleteTask(task)
        }
    }
}