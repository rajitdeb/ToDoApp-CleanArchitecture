package com.rajit.todoapp_cleanarchitecture.presentation.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task
import com.rajit.todoapp_cleanarchitecture.domain.usecase.AddTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.GetTasksUseCase
import com.rajit.todoapp_cleanarchitecture.domain.usecase.PinTaskUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val pinTaskUseCase: PinTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>> = _tasks

    private val _taskListObserver: Observer<List<Task>> = Observer { updatedList ->

        // Sorting the list firstly based on `isPinned`
        // Then by `id`
        val sortedList = updatedList.sortedWith(
            compareByDescending<Task>{ it.isPinned }.thenBy { it.id }
        )

        _tasks.value = sortedList
    }

    fun getAllTasks() {

        _tasks.removeObserver(_taskListObserver)

        viewModelScope.launch {
            val list = getTasksUseCase.execute()
            list.observeForever(_taskListObserver)
        }
    }

    suspend fun addNewTask(title: String, isPinned: Boolean) {
        coroutineScope {
            async { addTaskUseCase.execute(title, isPinned) }
        }.await()
    }

    suspend fun pinTask(task: Task, isPinned: Boolean) {
        coroutineScope {
            async { pinTaskUseCase.execute(task, isPinned) }
        }.await()
    }

    suspend fun deleteTask(task: Task) {
        coroutineScope {
            async { deleteTaskUseCase.execute(task) }
        }.await()
    }

    override fun onCleared() {
        super.onCleared()
        getTasksUseCase.execute().removeObserver(_taskListObserver)
    }

}