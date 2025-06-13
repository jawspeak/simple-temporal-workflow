import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import workflow.SimpleWorkflow
import workflow.SimpleWorkflowImpl

@Serializable
data class User(val name: String, val age: Int)

fun main() = runBlocking {
    // Start Temporal service
    val service = WorkflowServiceStubs.newLocalServiceStubs()
    val client = WorkflowClient.newInstance(service)
    val factory = WorkerFactory.newInstance(client)
    
    // Create a worker
    val worker = factory.newWorker("simple-task-queue")
    worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl::class.java)
    factory.start()
    
    // Start a workflow
    val workflow = client.newWorkflowStub(
        SimpleWorkflow::class.java,
        WorkflowOptions.newBuilder()
            .setTaskQueue("simple-task-queue")
            .build()
    )
    
    // Execute workflow
    val result = workflow.process("Hello, Temporal!")
    println("Workflow result: $result")
    
    // Original JSON example
    val user = User("John Doe", 30)
    val json = Json.encodeToString(User.serializer(), user)
    println("Serialized user: $json")
    
    val deserializedUser = Json.decodeFromString(User.serializer(), json)
    println("Deserialized user: $deserializedUser")
} 