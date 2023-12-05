package com.example.firstmacros.service.model

data class MealModel(
    var id: String,
    var name: String,
    var hour: Int,
    var minutes: Int,
    var items: List<ItemModel> = emptyList(),
)
