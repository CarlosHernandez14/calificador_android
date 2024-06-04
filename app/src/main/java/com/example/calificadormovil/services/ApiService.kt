package com.example.calificadormovil.services

import com.example.calificadormovil.Curso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("api/cursos")
    fun getCursos(): Call<List<Curso>>
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)
