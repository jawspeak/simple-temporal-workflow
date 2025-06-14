package xyz.block.simpletemporalworkflow

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface MyActivities {
    @ActivityMethod
    fun greet(name: String): String
}