package dev.huertabeja.users.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserRequest(
    val run: Int,
    val dv: Int,
    val nombres: String,      // Nombres completos (ej: "Juan Pablo")
    val apellidos: String,    // Apellidos completos (ej: "García López")
    val email: String,
    val telefono: Int,
    val password: String
)