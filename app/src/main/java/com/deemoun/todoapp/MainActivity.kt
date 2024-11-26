package com.deemoun.todoapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.deemoun.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dataStoreManager = DataStoreManager(this)

        setContent {
            ToDoAppTheme {
                var tasks by remember { mutableStateOf(setOf<String>()) }
                var isLinkToggleEnabled by remember { mutableStateOf(false) }

                // Load tasks on app startup
                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        dataStoreManager.tasks.collect { savedTasks ->
                            tasks = savedTasks
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("ToDoSlav") },
                            actions = {
                                // Add the toggle
                                Switch(
                                    checked = isLinkToggleEnabled,
                                    onCheckedChange = {
                                        isLinkToggleEnabled = it
                                        // Show a toast when toggled
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Link toggle is pressed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    TodoScreen(
                        tasks = tasks,
                        onTaskAdd = { newTask ->
                            val updatedTasks = tasks + newTask
                            tasks = updatedTasks
                            saveTasks(updatedTasks)
                        },
                        onTaskDelete = { taskToDelete ->
                            val updatedTasks = tasks - taskToDelete
                            tasks = updatedTasks
                            saveTasks(updatedTasks)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun saveTasks(tasks: Set<String>) {
        lifecycleScope.launch {
            dataStoreManager.saveTasks(tasks)
        }
    }
}

@Composable
fun TodoScreen(
    tasks: Set<String>,
    onTaskAdd: (String) -> Unit,
    onTaskDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var newTask by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input Field and Add Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter task") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() } // Collapse keyboard on pressing Done
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    onTaskAdd(newTask)
                    newTask = ""
                    Log.d("TodoApp","Task item added")
                    focusManager.clearFocus() // Collapse keyboard after adding a task
                }
            }) {
                Text("Add")
            }
        }

        // Task List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(tasks.toList()) { task ->
                TaskItem(
                    task = task,
                    onDelete = {
                        Log.d("TodoApp","Task item deleted")
                        onTaskDelete(task)
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: String, onDelete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = task,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp
        )
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoScreenPreview() {
    ToDoAppTheme {
        TodoScreen(
            tasks = setOf("Task 1", "Task 2"),
            onTaskAdd = {},
            onTaskDelete = {}
        )
    }
}