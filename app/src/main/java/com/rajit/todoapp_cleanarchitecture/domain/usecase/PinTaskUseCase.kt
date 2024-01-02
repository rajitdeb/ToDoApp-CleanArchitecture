package com.rajit.todoapp_cleanarchitecture.domain.usecase

import com.rajit.todoapp_cleanarchitecture.data.local.repository.RoomTaskRepository
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task

class PinTaskUseCase(private val taskRepository: RoomTaskRepository) {

    suspend fun execute(task: Task, isPinned: Boolean) {
        val updatedTask = task.copy(isPinned = isPinned)
        taskRepository.updateTask(updatedTask)
    }

}