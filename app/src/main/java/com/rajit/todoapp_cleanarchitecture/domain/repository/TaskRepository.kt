package com.rajit.todoapp_cleanarchitecture.domain.repository

import androidx.lifecycle.LiveData
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task

interface TaskRepository {

   fun getTasks(): LiveData<List<Task>>

   suspend fun addTask(task: Task)

   suspend fun updateTask(task: Task)

   suspend fun deleteTask(task: Task)

}