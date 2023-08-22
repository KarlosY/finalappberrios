package com.example.practica_ec3

import com.example.practica_ec3.model.Perro
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds")
    suspend fun getPerros(): List<Perro>

    @GET("breeds/{perroId}")
    suspend fun getPerroDetails(@Path("perroId") perroId: Int): Perro
}

