package sdui_backend.model

data class User(override val id: String, val email: String, val hashedPassword: ByteArray) : Model
