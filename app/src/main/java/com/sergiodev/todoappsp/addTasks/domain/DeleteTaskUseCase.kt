package com.sergiodev.todoappsp.addTasks.domain

import com.sergiodev.todoappsp.addTasks.data.TaskRepository
import com.sergiodev.todoappsp.addTasks.ui.model.TaskModel
import javax.inject.Inject




class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.delete(taskModel)
    }
}
