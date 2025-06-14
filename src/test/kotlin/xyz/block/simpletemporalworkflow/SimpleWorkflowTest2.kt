package xyz.block.simpletemporalworkflow

import io.temporal.testing.TestWorkflowEnvironment
import io.temporal.worker.Worker
import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleWorkflowTest2 {
    @Test
    fun `test simple workflow execution`() {
        val testEnv = TestWorkflowEnvironment.newInstance()
        val worker = testEnv.newWorker("simple-task-queue")
        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl::class.java)
        testEnv.start()
        
        val workflow = testEnv.workflowClient.newWorkflowStub(
            SimpleWorkflow::class.java,
            io.temporal.client.WorkflowOptions.newBuilder()
                .setTaskQueue("simple-task-queue")
                .build()
        )
        
        val result = workflow.process("test input")
        assertEquals("Processed: test input", result)
        
        testEnv.shutdown()
    }
}