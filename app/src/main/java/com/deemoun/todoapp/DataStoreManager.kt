package com.deemoun.todoapp

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance
val Context.dataStore by preferencesDataStore("todo_data_store")

class DataStoreManager(private val context: Context) {

    companion object {
        private val TASKS_KEY = stringSetPreferencesKey("tasks")
    }

    // Save tasks to DataStore
    suspend fun saveTasks(tasks: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[TASKS_KEY] = tasks
        }
    }

    // Get tasks from DataStore
    val tasks: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[TASKS_KEY] ?: emptySet()
    }
}
