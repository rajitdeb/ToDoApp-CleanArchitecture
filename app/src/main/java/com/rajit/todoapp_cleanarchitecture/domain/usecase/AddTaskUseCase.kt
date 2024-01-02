package com.rajit.todoapp_cleanarchitecture.domain.usecase

import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.domain.repository.TaskRepository

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(title: String, isPinned: Boolean) {
        val newTask = Task(title = title, isPinned = isPinned)
        taskRepository.addTask(newTask)
    }

}