package com.sergiodev.todoappsp.addTasks.domain

import com.sergiodev.todoappsp.addTasks.data.TaskRepository
import com.sergiodev.todoappsp.addTasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks

}