package com.rajit.todoapp_cleanarchitecture.domain.usecase

import com.rajit.todoapp_cleanarchitecture.data.local.repository.RoomTaskRepository
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task

class DeleteTaskUseCase(private val taskRepository: RoomTaskRepository) {

    suspend fun execute(task: Task) {
        taskRepository.deleteTask(task)
    }

}