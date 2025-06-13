package xyz.block.simpletemporalworkflow

import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface SimpleWorkflow {
    @WorkflowMethod
    fun process(input: String): String
}

class SimpleWorkflowImpl : SimpleWorkflow {
    override fun process(input: String): String {
        return "Processed: $input"
    }
}