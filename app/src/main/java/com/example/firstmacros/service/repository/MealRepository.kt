package com.example.firstmacros.service.repository

import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.MealModel
import com.example.firstmacros.service.repository.remote.MealService
import com.example.firstmacros.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository {

    private val remote = RetrofitClient.getService(MealService::class.java)

    fun getUserMeals(listener: APIListener<List<MealModel>>) {
        val call = remote.getUserMeals()

        call.enqueue(object : Callback<List<MealModel>> {
            override fun onResponse(call: Call<List<MealModel>>, response: Response<List<MealModel>>) {
                if (response.code() == AppConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure("Error at: MealRepository, getUserMeals(), onResponse")
                }
            }

            override fun onFailure(call: Call<List<MealModel>>, t: Throwable) {
                listener.onFailure("Error at: MealRepository, getUserMeals(), onFailure")
            }
        })
    }

    fun deleteMeal(id: String, listener: APIListener<MealModel>) {
        val call = remote.deleteMeal(id)

        call.enqueue(object : Callback<MealModel> {
            override fun onResponse(call: Call<MealModel>, response: Response<MealModel>) {
                try {
                    if (response.code() == AppConstants.HTTP.SUCCESS) {
                        response.body()?.let { listener.onSuccess(it) }
                    } else {
                        listener.onFailure("Error at: MealRepository, deleteMeal(), onSuccess")
                    }
                } catch (e: Exception) {
                    listener.onFailure("Error at: MealRepository, deleteMeal(), onSuccess catch")
                }
            }

            override fun onFailure(call: Call<MealModel>, t: Throwable) {
                listener.onFailure("Error at: MealRepository, deleteMeal(), onFailure")
            }
        })
    }
}