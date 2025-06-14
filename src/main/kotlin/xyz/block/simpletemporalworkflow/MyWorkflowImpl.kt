package xyz.block.simpletemporalworkflow

import io.temporal.activity.ActivityOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class MyWorkflowImpl : MyWorkflow {
    private val activities = Workflow.newActivityStub(
        MyActivities::class.java,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .build()
    )

    override fun runWorkflow(name: String): String {
        return activities.greet(name)
    }
}