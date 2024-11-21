package com.deemoun.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deemoun.todoapp.ui.theme.ToDoAppTheme

class LinksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                LinksScreen()
            }
        }
    }
}

@Composable
fun LinksScreen() {
    Text("This is the Links screen!")
}
