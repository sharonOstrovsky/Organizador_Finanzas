package com.example.organizador.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.organizador.data.model.TaskItem
import com.example.organizador.data.network.OrganizadorRepository
import kotlinx.coroutines.launch
import java.time.LocalDate


class TaskViewModel(
    private val repository: OrganizadorRepository
): ViewModel() {

    var taskItems : LiveData<List<TaskItem>> = repository.allTaskItem.asLiveData()



    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch{
        repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch{
        repository.updateTaskItem(taskItem)
    }
    fun deleteTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.deleteTaskItem(taskItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch{
        if(!taskItem.isCompleted()){
            taskItem.completeDateString = TaskItem.dateFormatter.format(LocalDate.now())
        }
        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(private val repository: OrganizadorRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unkown class for View Model")
    }
}