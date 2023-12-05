package com.example.firstmacros.service.listener

import com.example.firstmacros.service.model.MealModel

interface MealListener {
    fun onClick(meal: MealModel)
    fun onLongClick(meal: MealModel)
}