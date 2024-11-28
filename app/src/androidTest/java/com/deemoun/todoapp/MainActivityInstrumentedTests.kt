import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.deemoun.todoapp.MainActivity
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Forces tests to run in alphabetical order
class MainActivityInstrumentedTest {

    @get:Rule
    val driver = createAndroidComposeRule<MainActivity>()

    @Test
    fun test01_DeleteFirstTask() { // Renamed to ensure it runs first
        // Add a task
        //val taskInputField = driver.onNodeWithText("Enter task")
        val taskInputField = driver.onNodeWithContentDescription("EnterTaskField")
        val addButton = driver.onNodeWithContentDescription("AddButton")
        taskInputField.performTextInput("Task1")
        addButton.performClick()

        // Wait for the UI to update
        driver.waitForIdle()

        // Verify the task is removed
        driver.onAllNodesWithContentDescription("DeleteButton")[0].performClick()

        driver.waitForIdle()

        driver.onAllNodesWithContentDescription("EmptyTaskList").onFirst().assertExists()
    }

    @Test
    fun test02_AddTask() { // Renamed to ensure correct order
        // Find the input field and add button
        val taskInputField = driver.onNodeWithContentDescription("EnterTaskField")
        val addButton = driver.onNodeWithContentDescription("AddButton")

        // Add a task
        taskInputField.performTextInput("Test Task")
        addButton.performClick()

        // Verify the task is added
        driver.onNodeWithText("Test Task").assertExists()
    }

    @Test
    fun test03_UIComponents() { // Renamed to ensure correct order
        // Check if all key components are displayed
        driver.onNodeWithContentDescription("EnterTaskField").assertExists()
        driver.onNodeWithContentDescription("AddButton").assertExists()
        driver.onNodeWithContentDescription("LinksButton").assertExists()
    }
}
