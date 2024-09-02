package com.sergiodev.todoappsp.addTasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.sergiodev.todoappsp.addTasks.ui.model.TaskModel


@Composable
fun TasksScreen(taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TasksUIState>(
        initialValue = TasksUIState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel.uiState
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TasksUIState.Error -> {}
        is TasksUIState.Loading -> {
            CircularProgressIndicator()
        }

        is TasksUIState.Success -> {

            Box(modifier = Modifier.fillMaxSize()) {

                AddTaskDialog(show = showDialog, onDismiss = {
                    taskViewModel.onDialogClose()
                }, onTaskAdd = { task ->
                    taskViewModel.onTaskAdd(task)
                })

                FabDialog(taskViewModel, Modifier.align(Alignment.BottomEnd))
                TasksList((uiState as TasksUIState.Success).list, taskViewModel)
            }
        }
    }


}

@Composable
fun TasksList(tasks: List<TaskModel>, taskViewModel: TaskViewModel) {

    //val myTasks: List<TaskModel> = taskViewModel.task

    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            ItemTask(taskViewModel = taskViewModel, taskModel = task)
        }

    }
}


@Composable
fun ItemTask(taskViewModel: TaskViewModel, taskModel: TaskModel) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            Checkbox(checked = taskModel.selected, onCheckedChange = {
                taskViewModel.onCheckBoxSelected(taskModel)
            })

        }

    }

}


@Composable
fun FabDialog(taskViewModel: TaskViewModel, modifier: Modifier) {

    FloatingActionButton(modifier = modifier.padding(16.dp), onClick = {
        taskViewModel.onDialogOpen()

    }) {
        Icon(Icons.Filled.Add, contentDescription = "")

    }

}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdd: (String) -> Unit) {

    var myTask by rememberSaveable {
        mutableStateOf("")

    }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTask, onValueChange = { myTask = it }, singleLine = true, maxLines = 1)
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {

                    onTaskAdd(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir")
                }

            }

        }
    }

}


