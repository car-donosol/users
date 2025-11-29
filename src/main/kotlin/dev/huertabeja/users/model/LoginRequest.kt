package dev.huertabeja.users.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val user: String,
    val password: String
)
