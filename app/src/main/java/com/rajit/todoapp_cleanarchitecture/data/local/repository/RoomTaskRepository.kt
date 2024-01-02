package com.rajit.todoapp_cleanarchitecture.data.local.repository

import androidx.lifecycle.LiveData
import com.rajit.todoapp_cleanarchitecture.data.local.dao.TaskDao
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.domain.repository.TaskRepository

class RoomTaskRepository(
    private val taskDao: TaskDao
): TaskRepository {
    override fun getTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun addTask(task: Task) {
        return taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }

}