package sdui_backend.model

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class BackendError(val errorCode: Int, val message: String)

fun BackendError(statusCode: HttpStatusCode, message: String) = BackendError(statusCode.value, message)
