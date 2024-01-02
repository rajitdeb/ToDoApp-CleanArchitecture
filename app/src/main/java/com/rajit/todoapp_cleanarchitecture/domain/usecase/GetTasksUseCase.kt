package com.rajit.todoapp_cleanarchitecture.domain.usecase

import androidx.lifecycle.LiveData
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.domain.repository.TaskRepository

class GetTasksUseCase(private val taskRepository: TaskRepository) {

    fun execute(): LiveData<List<Task>> {
        return taskRepository.getTasks()
    }

}