package com.desafiolatam.coroutines.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiolatam.coroutines.data.TaskEntity
import com.desafiolatam.coroutines.repository.TaskRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepositoryImp
) : ViewModel() {

    private val _data: MutableStateFlow<List<TaskEntity>> = MutableStateFlow(emptyList())
    val taskListStateFlow: StateFlow<List<TaskEntity>> = _data.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTasks().collectLatest {
                _data.value = it
            }
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.addTask(task)
            // Actualiza la lista de tareas después de agregar una nueva
            repository.getTasks().collectLatest {
                _data.value = it
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
            // Actualiza la lista de tareas después de eliminar
            repository.getTasks().collectLatest {
                _data.value = it
            }
        }
    }
    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.isTaskCompleted(task, task.isCompleted)
            // Actualiza la lista de tareas después de hacer check
            repository.getTasks().collectLatest {
                _data.value = it
            }
        }
    }

}