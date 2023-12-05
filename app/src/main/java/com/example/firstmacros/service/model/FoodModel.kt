package com.example.firstmacros.service.model

import com.google.gson.annotations.SerializedName

data class FoodModel(
    var id: String,
    var name: String,
    var kcal: Double,
    var carb: Double,
    var prot: Double,
    @SerializedName("fat")
    var fats: Double,
)