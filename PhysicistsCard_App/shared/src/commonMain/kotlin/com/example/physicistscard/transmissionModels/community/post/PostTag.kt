import kotlinx.serialization.Serializable

@Serializable
data class PostTag(
    val tagId: String,
    val name: String
)