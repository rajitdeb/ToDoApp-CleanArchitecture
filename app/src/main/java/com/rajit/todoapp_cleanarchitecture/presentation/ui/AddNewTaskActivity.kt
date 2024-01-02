package com.rajit.todoapp_cleanarchitecture.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rajit.todoapp_cleanarchitecture.data.local.database.AppDatabase
import com.rajit.todoapp_cleanarchitecture.data.local.repository.RoomTaskRepository
import com.rajit.todoapp_cleanarchitecture.databinding.ActivityAddNewTaskBinding
import com.rajit.todoapp_cleanarchitecture.domain.usecase.AddTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.GetTasksUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.PinTaskUseCase
import com.rajit.todoapp_cleanarchitecture.presentation.presenter.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewTaskActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAddNewTaskBinding

    private lateinit var db: AppDatabase
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNewTaskBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        db = AppDatabase.invoke(applicationContext)

        val taskDao = db.taskDao()

        val roomTaskRepository = RoomTaskRepository(taskDao)

        taskViewModel = TaskViewModel(
            GetTasksUseCase(roomTaskRepository),
            AddTaskUseCase(roomTaskRepository),
            PinTaskUseCase(roomTaskRepository),
            DeleteTaskUseCase(roomTaskRepository)
        )

        _binding.saveTaskBtn.setOnClickListener {
            // save data to db
            saveDataToDB()

            // close this activity
            finish()
        }

    }

    private fun saveDataToDB() {
        val title = _binding.titleEdt.editText?.text?.trim().toString()

        if(title.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                taskViewModel.addNewTask(title, false)
            }
        }

    }
}