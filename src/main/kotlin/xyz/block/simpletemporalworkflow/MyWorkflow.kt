package xyz.block.simpletemporalworkflow

import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface MyWorkflow {
    @WorkflowMethod
    fun runWorkflow(name: String): String
}