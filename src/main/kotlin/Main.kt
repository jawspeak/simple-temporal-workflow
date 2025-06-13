import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class User(val name: String, val age: Int)

fun main() = runBlocking {
    val user = User("John Doe", 30)
    val json = Json.encodeToString(User.serializer(), user)
    println("Serialized user: $json")
    
    val deserializedUser = Json.decodeFromString(User.serializer(), json)
    println("Deserialized user: $deserializedUser")
} 