package com.sergiodev.todoappsp.addTasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiodev.todoappsp.addTasks.domain.AddTaskUseCase
import com.sergiodev.todoappsp.addTasks.domain.GetTaskUseCase
import com.sergiodev.todoappsp.addTasks.ui.TasksUIState.Success
import com.sergiodev.todoappsp.addTasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    private val _showDialog: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

//    private val _tasks = mutableStateListOf<TaskModel>()
//    val task: List<TaskModel> = _tasks

    val uiState: StateFlow<TasksUIState> = getTaskUseCase()
        .map(::Success)
        .catch { TasksUIState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUIState.Loading)


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onDialogOpen() {
        _showDialog.value = true
    }

    fun onTaskAdd(task: String) {
        onDialogClose()
        Log.i(this.javaClass.name, "onTaskAdd -> $task")
        //_tasks.add(TaskModel(task = task))

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
//
//        val index = _tasks.indexOf(taskModel)
//
//        _tasks[index] = _tasks[index].let {
//            it.copy(selected = !it.selected)
//        }

    }

    fun onItemRemove(taskModel: TaskModel) {

//        val task = _tasks.find { x -> x.id == taskModel.id }
//        _tasks.remove(task)
    }


}