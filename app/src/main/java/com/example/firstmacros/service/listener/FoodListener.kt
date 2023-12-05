package com.example.firstmacros.service.listener

import com.example.firstmacros.service.model.FoodModel


interface FoodListener {
    fun onClick(food: FoodModel)
    fun onLongClick(food: FoodModel)
}