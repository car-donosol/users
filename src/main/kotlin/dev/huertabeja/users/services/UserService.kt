package dev.huertabeja.users.services

import dev.huertabeja.users.data.UserRepository
import dev.huertabeja.users.model.User
import org.springframework.stereotype.Service
import dev.huertabeja.users.model.UserRequest
import dev.huertabeja.users.model.LoginRequest
import dev.huertabeja.users.model.LoginResponse

@Service
class UserService(private val repository: UserRepository) {

    suspend fun getUsers(): List<User> {
        return repository.getUsersFromSupabase()
    }

    suspend fun createUser(user: UserRequest): User {
        return repository.createUser(user)
    }

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        val user = repository.loginUser(loginRequest.email, loginRequest.password)
        
        return if (user != null) {
            LoginResponse(
                success = true,
                message = "Login exitoso",
                user = user
            )
        } else {
            LoginResponse(
                success = false,
                message = "Usuario no existe o credenciales incorrectas",
                user = null
            )
        }
    }
}