package sdui_backend.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteTokenResponse(
    val token: String,
    val validUntil: Long,
    val routes: List<String>
)
