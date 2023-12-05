package com.example.firstmacros.service.repository.remote

import androidx.room.Delete
import com.example.firstmacros.service.model.MealModel
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MealService {

    @GET("meal")
    fun getUserMeals(): Call<List<MealModel>>

    @POST("meal")
    fun createMeal(): Call<MealModel>

    @DELETE("meal")
    fun deleteMeal(
        @Query("meal_id") id: String
    ): Call<MealModel>
}