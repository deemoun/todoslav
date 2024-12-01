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

    // Common locators for all tests
    private val taskInputField by lazy { driver.onNodeWithContentDescription("EnterTaskField") }
    private val addButton by lazy { driver.onNodeWithContentDescription("AddButton") }
    private val deleteButton by lazy { driver.onAllNodesWithContentDescription("DeleteButton") }
    private val emptyTaskList by lazy { driver.onAllNodesWithContentDescription("EmptyTaskList") }
    private val linksButton by lazy { driver.onNodeWithContentDescription("LinksButton") }

    @Test
    fun test01_DeleteFirstTask() {
        // Add a task
        taskInputField.performTextInput("Task1")
        addButton.performClick()

        // Delete the task
        deleteButton[0].performClick()

        // Verify that the task list is empty
        emptyTaskList.onFirst().assertExists()
    }

    @Test
    fun test02_AddTask() {
        // Add a task
        taskInputField.performTextInput("TestTask")
        addButton.performClick()

        // Verify the task is added
        driver.onNodeWithText("TestTask").assertExists()
    }

    @Test
    fun test03_UIComponents() {
        // Check if all key components are displayed
        taskInputField.assertExists()
        addButton.assertExists()
        linksButton.assertExists()
    }
}
