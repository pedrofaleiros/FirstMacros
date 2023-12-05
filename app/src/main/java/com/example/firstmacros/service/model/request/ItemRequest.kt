package com.example.firstmacros.service.model.request

import com.google.gson.annotations.SerializedName

data class ItemRequest(
    @SerializedName("food_id") val foodId: String = "",
    @SerializedName("meal_id") val mealId: String,
    val amount: Double,
)