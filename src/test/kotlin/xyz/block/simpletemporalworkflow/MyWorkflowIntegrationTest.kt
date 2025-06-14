package xyz.block.simpletemporalworkflow

import io.temporal.client.WorkflowClient
import io.temporal.testing.TestWorkflowEnvironment
import io.temporal.worker.WorkerFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MyWorkflowIntegrationTest {

    private lateinit var testEnv: TestWorkflowEnvironment
    private lateinit var client: WorkflowClient

    @BeforeEach
    fun setup() {
        testEnv = TestWorkflowEnvironment.newInstance()
        client = testEnv.workflowClient

        val factory = WorkerFactory.newInstance(client)
        val worker = factory.newWorker("test-task-queue")

        // Register workflow + activities
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl::class.java)
        worker.registerActivitiesImplementations(MyActivitiesImpl())

        factory.start()
    }

    @AfterEach
    fun teardown() {
        testEnv.close()
    }

    @Test
    fun `test simple workflow`() {
        val stub = client.newWorkflowStub(
            MyWorkflow::class.java,
            WorkflowClient.newWorkflowOptions {
                setTaskQueue("test-task-queue")
            }
        )

        val result = stub.runWorkflow("Kotlin!")
        assertEquals("Hello Kotlin!", result)
    }
}