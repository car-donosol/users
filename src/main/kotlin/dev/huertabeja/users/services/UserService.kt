package dev.huertabeja.users.services

import dev.huertabeja.users.data.UserRepository
import dev.huertabeja.users.model.User
import org.springframework.stereotype.Service
import dev.huertabeja.users.model.UserRequest
import dev.huertabeja.users.model.LoginRequest
import dev.huertabeja.users.model.LoginResponse
import dev.huertabeja.users.exception.UserAlreadyExistsException

@Service
class UserService(private val repository: UserRepository) {

    suspend fun getUsers(): List<User> {
        return repository.getUsersFromSupabase()
    }

    suspend fun getUserById(id: String): User? {
        return repository.getUserById(id)
    }

    suspend fun createUser(user: UserRequest): User {
        // Verificar si el RUT ya existe
        val existingUserByRun = repository.findByRun(user.run)
        
        if (existingUserByRun != null) {
            throw UserAlreadyExistsException("Usuario con RUN ${user.run}-${user.dv} ya existe en el sistema")
        }
        
        // Verificar si el email ya existe
        val existingUserByEmail = repository.findByEmail(user.email)
        
        if (existingUserByEmail != null) {
            throw UserAlreadyExistsException("El email ${user.email} ya está registrado en el sistema")
        }
        
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