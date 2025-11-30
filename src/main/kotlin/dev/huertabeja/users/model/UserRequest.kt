package dev.huertabeja.users.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserRequest(
    val run: Int,
    val dv: Int,
    @SerialName("pnombre")
    val pnombre: String,  // Primer nombre
    @SerialName("snombre")
    val snombre: String?,  // Segundo nombre (opcional)
    @SerialName("appaterno")
    val appaterno: String,  // Apellido paterno
    @SerialName("apmaterno")
    val apmaterno: String,  // Apellido materno
    val email: String,
    val telefono: Int,
    val password: String
)