package com.example.firstmacros.service.model

data class ItemModel(
    var id: String,
    var amount: Double,
    var food: FoodModel = FoodModel("", "", 0.0, 0.0, 0.0, 0.0),
)
