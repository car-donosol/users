package dev.huertabeja.users.data

import dev.huertabeja.users.model.User
import dev.huertabeja.users.model.UserRequest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import org.springframework.stereotype.Repository


@Repository
class UserRepository(private val supabase: SupabaseClient) {

    suspend fun getUsersFromSupabase(): List<User> {
        return try {
            val response = supabase.from("Clientes").select()

            response.decodeList<User>()
        } catch (e: Exception) {
            println("Error al obtener clientes de Supabase: ${e.message}")
            throw e
        }
    }

    suspend fun createUser(user: UserRequest): User {
        return try {
            val response = supabase.from("Clientes").insert(user) {
                select()
            }
            response.decodeSingle<User>()
        } catch (e: Exception) {
            println("Error al crear cliente en Supabase: ${e.message}")
            throw e
        }
    }

    suspend fun loginUser(email: String, password: String): User? {
        return try {
            val response = supabase.from("Clientes")
                .select {
                    filter {
                        eq("email", email)
                        eq("password", password)
                    }
                }
            
            val users = response.decodeList<User>()
            users.firstOrNull()
        } catch (e: Exception) {
            println("Error al hacer login en Supabase: ${e.message}")
            null
        }
    }

    suspend fun findByRun(run: Int): User? {
        return try {
            val response = supabase.from("Clientes")
                .select {
                    filter {
                        eq("run", run)
                    }
                }
            
            val users = response.decodeList<User>()
            users.firstOrNull()
        } catch (e: Exception) {
            println("Error al buscar cliente por RUN en Supabase: ${e.message}")
            null
        }
    }

    suspend fun findByEmail(email: String): User? {
        return try {
            val response = supabase.from("Clientes")
                .select {
                    filter {
                        eq("email", email)
                    }
                }
            
            val users = response.decodeList<User>()
            users.firstOrNull()
        } catch (e: Exception) {
            println("Error al buscar cliente por email en Supabase: ${e.message}")
            null
        }
    }

}
