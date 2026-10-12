package com.simkbm.ustaz.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SupabaseAuthResponse(
    val access_token: String? = null,
    val refresh_token: String? = null,
    val user: SupabaseUser? = null,
    val error: String? = null,
    val error_description: String? = null
)

@Serializable
data class SupabaseUser(
    val id: String? = null,
    val email: String? = null,
    val role: String? = null
)

@Serializable
data class SupabaseError(
    val error: String? = null,
    val error_description: String? = null,
    val message: String? = null
)

class SupabaseClient(
    private val url: String,
    private val anonKey: String
) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.NONE
        }
        install(DefaultRequest) {
            header("apikey", anonKey)
            header("Authorization", "Bearer $anonKey")
            contentType(ContentType.Application.Json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 30000
        }
    }

    // ==================== AUTH ====================

    suspend fun signInWithEmail(email: String, password: String): Result<SupabaseAuthResponse> {
        return try {
            val response = client.post("$url/auth/v1/token?grant_type=password") {
                setBody(mapOf("email" to email, "password" to password))
            }
            val result = response.body<SupabaseAuthResponse>()
            if (result.access_token != null) {
                Result.success(result)
            } else {
                Result.failure(Exception(result.error_description ?: result.error ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String): Result<SupabaseAuthResponse> {
        return try {
            val response = client.post("$url/auth/v1/signup") {
                setBody(mapOf("email" to email, "password" to password))
            }
            val result = response.body<SupabaseAuthResponse>()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun lookupEmail(idLogin: String): Result<String> {
        return try {
            val response = client.post("$url/functions/v1/lookup-email") {
                setBody(mapOf("id_login" to idLogin))
            }
            if (response.status == HttpStatusCode.OK) {
                val body = response.bodyAsText()
                // Parse email from response
                val email = body.substringAfter("\"email\":\"").substringBefore("\"")
                if (email.isNotEmpty()) {
                    Result.success(email)
                } else {
                    Result.failure(Exception("ID Login tidak ditemukan"))
                }
            } else {
                Result.failure(Exception("Gagal mencari ID Login"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ==================== DATA OPERATIONS ====================

    suspend fun getData(table: String, accessToken: String): Result<String> {
        return try {
            val response = client.get("$url/rest/v1/$table") {
                header("Authorization", "Bearer $accessToken")
                parameter("select", "*")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.bodyAsText())
            } else {
                Result.failure(Exception("Failed to fetch $table: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertData(table: String, data: String, accessToken: String): Result<Unit> {
        return try {
            val response = client.post("$url/rest/v1/$table") {
                header("Authorization", "Bearer $accessToken")
                header("Prefer", "return=minimal")
                setBody(data)
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to insert: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateData(table: String, id: String, data: String, accessToken: String): Result<Unit> {
        return try {
            val response = client.patch("$url/rest/v1/$table") {
                header("Authorization", "Bearer $accessToken")
                parameter("id", "eq.$id")
                setBody(data)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to update: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteData(table: String, id: String, accessToken: String): Result<Unit> {
        return try {
            val response = client.delete("$url/rest/v1/$table") {
                header("Authorization", "Bearer $accessToken")
                parameter("id", "eq.$id")
            }
            if (response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun close() {
        client.close()
    }
}
