import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `test user serialization and deserialization`() {
        val user = User("John Doe", 30)
        val json = Json.encodeToString(User.serializer(), user)
        val deserializedUser = Json.decodeFromString(User.serializer(), json)
        
        assertEquals(user, deserializedUser)
        assertEquals("John Doe", deserializedUser.name)
        assertEquals(30, deserializedUser.age)
    }
} 