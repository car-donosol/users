package dev.huertabeja.users.services

import dev.huertabeja.users.data.UserRepository
import dev.huertabeja.users.model.User
import org.springframework.stereotype.Service
import dev.huertabeja.users.model.UserRequest
import dev.huertabeja.users.model.UserRequestInternal
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
        
        // Lógica de negocio: Separar nombres
        val nombresList = user.nombres.trim().split("\\s+".toRegex())
        val primerNombre = nombresList.getOrNull(0)?.trim() ?: ""
        val segundoNombre = nombresList.getOrNull(1)?.trim()
        
        // Lógica de negocio: Separar apellidos
        val apellidosList = user.apellidos.trim().split("\\s+".toRegex())
        val apellidoPaterno = apellidosList.getOrNull(0)?.trim() ?: ""
        val apellidoMaterno = apellidosList.getOrNull(1)?.trim()
        
        // Crear objeto interno con campos separados
        val userInternal = UserRequestInternal(
            run = user.run,
            dv = user.dv,
            pnombre = primerNombre,
            snombre = segundoNombre,
            appaterno = apellidoPaterno,
            apmaterno = apellidoMaterno,
            email = user.email,
            telefono = user.telefono,
            password = user.password
        )
        
        return repository.createUser(userInternal)
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