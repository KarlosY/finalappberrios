package com.example.practica_ec3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    fun createDogApiService(): DogApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DogApiService::class.java)
    }
}