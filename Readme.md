# ToDoSlav App

**ToDoSlav** is a lightweight task management application built using Jetpack Compose and Android DataStore. It provides an intuitive interface for creating, managing, and persisting tasks locally.

**YouTube** [video lesson about the ToDoSlav app](https://www.youtube.com/watch?v=2poIKIdfSZQ) test automation (*in Russian with CC*) 

---

## Table of Contents
- [Overview](#overview)
- [Core Components](#core-components)
- [How the App Functions](#how-the-app-functions)
- [Testing](#testing)
- [Contribution](#contribution)

---

## Overview

ToDoSlav allows users to:
- Add tasks via a text input field and an "Add" button.
- View tasks in a scrollable list.
- Delete tasks using a "Delete" button.
- Persist tasks locally using Android DataStore.

The app uses modern Android development practices, including Jetpack Compose for UI, Kotlin Coroutines for asynchronous operations, and Compose UI testing for reliability.

---

## Core Components

### 1. **MainActivity**
The central component orchestrating the app's lifecycle, UI, and data interactions.

- **Key Methods**:
  - `onCreate`: Initializes dependencies and sets up the UI.
  - `saveTasks`: Saves the current task list using `DataStoreManager`.

### 2. **TodoScreen**
A composable function that encapsulates the main task management interface.

- **Features**:
  - **Task Input**: Enter new tasks in a text field.
  - **Add Button**: Adds tasks to the list after validation.
  - **Task List**: Displays all tasks with individual delete buttons.
  - **Empty State Message**: Prompts users to add their first task when the list is empty.

### 3. **DataStoreManager**
Handles persistent storage of tasks using Android DataStore.

- **Key Functions**:
  - `saveTasks`: Persists a set of tasks asynchronously.
  - `tasks`: A `Flow` emitting the current task list.
  - `clearTasks`: Deletes all stored tasks.

### 4. **TaskItem**
A reusable composable function for rendering individual tasks.

- **Features**:
  - Displays the task text.
  - Includes a "Delete" button to remove the task.

---

## How the App Functions

### Adding a Task
1. User enters text in the input field.
2. Pressing the "Add" button triggers the `onTaskAdd` callback.
3. The task is validated, added to the `tasks` state, and persisted using `DataStoreManager.saveTasks`.

### Deleting a Task
1. User clicks the "Delete" button next to a task.
2. The `onTaskDelete` callback is invoked, removing the task from the `tasks` state.
3. The updated task list is persisted using `saveTasks`.

### Loading Tasks
1. On app startup, tasks are loaded from `DataStoreManager.tasks` (a `Flow`).
2. `LaunchedEffect` collects the tasks and updates the `tasks` state.
3. The UI dynamically renders the retrieved tasks.

### State Management
The app uses Compose's `mutableStateOf` to maintain reactive states for tasks. Any change to the state triggers an automatic UI update.

---

## Testing

### Instrumented Tests
The app includes a robust suite of UI tests to verify its functionality. Key tests are located in `MainActivityInstrumentedTests.kt`.

- **`test01_DeleteFirstTask`**:
  - Adds and deletes a task, verifying the task list updates correctly.

- **`test02_AddTask`**:
  - Simulates adding a task and asserts that it appears in the list.

- **`test03_UIComponents`**:
  - Checks the presence of critical UI elements like the input field, add button, and links button.

### Test Environment
- Compose UI Test framework is used.
- Tests are run in an Android emulator or physical device.

### How to Run Tests
1. Open the project in Android Studio.
2. Locate `MainActivityInstrumentedTests.kt`.
3. Run the tests using the Android Studio test runner.

---

## Contribution

Contributions are welcome! If you find issues or have ideas for improvement, feel free to:
- Open an issue on GitHub.
- Submit a pull request.

---
