package com.sergiodev.todoappsp.addTasks.ui

import com.sergiodev.todoappsp.addTasks.ui.model.TaskModel

sealed interface TasksUIState {

    data object Loading : TasksUIState
    data class Error(val throwable: Throwable) : TasksUIState
    data class Success(val list: List<TaskModel>) : TasksUIState

}