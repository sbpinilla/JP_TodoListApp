package com.sergiodev.todoappsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.sergiodev.todoappsp.addTasks.ui.TaskViewModel
import com.sergiodev.todoappsp.addTasks.ui.TasksScreen
import com.sergiodev.todoappsp.ui.theme.TodoAppSpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val taskViewModel: TaskViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppSpTheme {
                TasksScreen(taskViewModel = taskViewModel)
            }
        }
    }
}
