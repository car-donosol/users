package dev.huertabeja.users.model

import dev.huertabeja.users.model.UserRequest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val user: String,
    val password: String
)