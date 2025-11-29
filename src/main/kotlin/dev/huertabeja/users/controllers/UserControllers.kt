package dev.huertabeja.users.controllers

import dev.huertabeja.users.model.User
import dev.huertabeja.users.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import dev.huertabeja.users.model.UserRequest
import dev.huertabeja.users.model.LoginRequest
import dev.huertabeja.users.model.LoginResponse


@RestController
@RequestMapping("/users") // Esto define que la URL base es /users
class UserControllers(
    private val service: UserService // Inyección de dependencias de Spring
) {

    // Cuando se haga una petición GET a /users, se ejecutará esta función
    @GetMapping
    suspend fun getUsers(): List<User> {
        return service.getUsers()
    }

    // Cuando se haga una petición POST a /users, se ejecutará esta función
    @PostMapping
    suspend fun createUser(@RequestBody user: UserRequest): User {
        return service.createUser(user)
    }

    // Endpoint de login - POST a /users/login
    @PostMapping("/login")
    suspend fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return service.loginUser(loginRequest)
    }
}