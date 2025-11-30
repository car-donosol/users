package dev.huertabeja.users.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class User(
    val id: String? = null,
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
    val fechareg: String,  // Fecha de registro en formato "YYYY-MM-DD"
    val password: String
)
