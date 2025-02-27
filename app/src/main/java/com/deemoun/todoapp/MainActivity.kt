package com.deemoun.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
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
                                TextButton(
                                    onClick = {
                                        startActivity(Intent(this@MainActivity, LinksActivity::class.java))
                                    },
                                    Modifier.semantics { contentDescription = "LinksButton" }
                                ) {
                                    Text(
                                        "Links",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                }
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
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = "EnterTaskField" },
                label = { Text("Enter task") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        onTaskAdd(newTask)
                        newTask = ""
                        Log.d("TodoApp", "Task item added")
                        focusManager.clearFocus()
                    }
                },
                Modifier.semantics { contentDescription = "AddButton" }
            ) {
                Text("Add")
            }
        }

        // Task List or Placeholder
        if (tasks.isEmpty()) {
            Text(
                text = "Add a first task",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .semantics { contentDescription = "EmptyTaskList" },
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center // Corrected alignment here
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(tasks.toList()) { task ->
                    TaskItem(
                        task = task,
                        onDelete = {
                            Log.d("TodoApp", "Task item deleted")
                            onTaskDelete(task)
                        }
                    )
                }
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
        Button(
            onClick = onDelete,
            Modifier.semantics { contentDescription = "DeleteButton" }
        ) {
            Text("Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoScreenPreview() {
    ToDoAppTheme {
        TodoScreen(
            tasks = emptySet(), // Show empty state
            onTaskAdd = {},
            onTaskDelete = {}
        )
    }
}
