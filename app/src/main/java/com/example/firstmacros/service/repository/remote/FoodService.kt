package com.example.firstmacros.service.repository.remote

import com.example.firstmacros.service.model.FoodModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {

    @GET("food")
    suspend fun getFood(): Response<List<FoodModel>>

    @GET("food")
    fun getFoods(): Call<List<FoodModel>>

    @GET("food/search")
    fun searchFoods(@Query("name") name: String): Call<List<FoodModel>>
}