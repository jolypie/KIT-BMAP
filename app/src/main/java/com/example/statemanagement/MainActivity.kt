package com.example.statemanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewModelScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TasksScreen()
            }
        }
    }
}

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean
)

class TasksViewModel : ViewModel() {
    var tasks by mutableStateOf<List<Task>>(emptyList())
    var isLoading by mutableStateOf(false)
    private var nextId = 1

    fun loadTasks() {
        viewModelScope.launch {
            isLoading = true
            delay(2000)
            tasks = listOf(
                Task(1, "Koupit mléko", false),
                Task(2, "Uklidit pokoj", true),
                Task(3, "Napsat úkol", false)
            )
            nextId = 4
            isLoading = false
        }
    }

    fun addTask(title: String) {
        val newTask = Task(nextId++, title, false)
        tasks = tasks + newTask
    }

    fun toggleTaskDone(taskId: Int) {
        tasks = tasks.map {
            if (it.id == taskId) it.copy(isDone = !it.isDone) else it
        }
    }
}

@Composable
fun TasksScreen(viewModel: TasksViewModel = viewModel()) {
    val tasks = viewModel.tasks
    val isLoading = viewModel.isLoading
    var newTaskText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks) { task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { viewModel.toggleTaskDone(task.id) }
                        )
                        Text(text = task.title, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = newTaskText,
                    onValueChange = { newTaskText = it },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (newTaskText.isNotBlank()) {
                            viewModel.addTask(newTaskText)
                            newTaskText = ""
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Přidat")
                }
            }
        }
    }
}
