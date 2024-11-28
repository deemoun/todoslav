import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.deemoun.todoapp.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun testAddTask() {
        // Find the input field and add button
        val taskInputField = composeTestRule.onNodeWithText("Enter task")
        val addButton = composeTestRule.onNodeWithText("Add")

        // Add a task
        taskInputField.performTextInput("Test Task")
        addButton.performClick()

        // Verify the task is added
        composeTestRule.onNodeWithText("Test Task").assertExists()
    }

    @Test
    fun testDeleteFirstTask() {
        // TODO: Fix the test. Currently it doesn't really checks if it was removed
        // Add multiple tasks
        val taskInputField = composeTestRule.onNodeWithText("Enter task")
        val addButton = composeTestRule.onNodeWithText("Add")
        taskInputField.performTextInput("Task 1")
        addButton.performClick()

        // Delete the first task
        composeTestRule.onAllNodesWithText("Delete")[0].performClick()

        // Verify the task is removed
        composeTestRule.onAllNodesWithText("Delete")[0].assertExists()
    }

    @Test()
    fun testUIComponents() {
        // Check if all key components are displayed
        composeTestRule.onNodeWithText("Enter task").assertExists()
        composeTestRule.onNodeWithText("Add").assertExists()
        composeTestRule.onNodeWithText("Links").assertExists()
    }
}
