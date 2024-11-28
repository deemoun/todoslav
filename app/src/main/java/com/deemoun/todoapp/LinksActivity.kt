package com.deemoun.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deemoun.todoapp.ui.theme.ToDoAppTheme

class LinksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                LinksScreen(
                    onBackClick = { finish() } // Close the activity on back button click
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinksScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Links") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.Close, // Use a close icon
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "To be added soon",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp) // Added additional padding
        )
    }
}
