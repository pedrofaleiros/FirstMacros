package com.example.firstmacros.service.repository

import android.util.Log
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.FoodModel
import com.example.firstmacros.service.repository.remote.FoodService
import com.example.firstmacros.service.repository.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRepository {
    private val remote = RetrofitClient.getService(FoodService::class.java)

    companion object {
        private var foodsCache: List<FoodModel>? = null

        fun getFoodsCache(): List<FoodModel>? {
            return foodsCache
        }

        fun setFoodsCache(foods: List<FoodModel>) {
            foodsCache = foods
        }
    }

    suspend fun getFoodsCoroutine(): List<FoodModel>? {
        val cache = getFoodsCache()
        if (cache != null) {
            return cache
        }

        return try {
            withContext(Dispatchers.IO) {
                val response = remote.getFood()
                if (response.isSuccessful) {
                    response.body()?.let {
                        setFoodsCache(it)
                    }
                    response.body()
                } else {
                    Log.e("getFoodsCoroutine", "Response not successful: ${response.errorBody()?.string()}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("getFoodsCoroutine", "Error fetching foods", e)
            null
        }
    }

    fun getFoods(listener: APIListener<List<FoodModel>>) {
        val cache = getFoodsCache()
        if (cache != null) {
            listener.onSuccess(cache)
        } else {
            val call = remote.getFoods()
            call.enqueue(object : Callback<List<FoodModel>> {
                override fun onResponse(call: Call<List<FoodModel>>, response: Response<List<FoodModel>>) {
                    if (response.code() == AppConstants.HTTP.SUCCESS) {
                        response.body()?.let {
//                            setFoodsCache(it)
                            listener.onSuccess(it)
                        }
                    } else {
                        listener.onFailure("Error at: FoodRepository, getFoods(), onResponse")
                        Log.d("PEDRO", "Error at: FoodRepository, getFoods(), onResponse")
                    }
                }

                override fun onFailure(call: Call<List<FoodModel>>, t: Throwable) {
                    listener.onFailure("Error at: FoodRepository, getFoods(), onFailure")
                    Log.d("PEDRO", "Error at: FoodRepository, getFoods(), onFailure")
                }
            })
        }
    }

    fun searchFoods(name: String, listener: APIListener<List<FoodModel>>) {
        val call = remote.searchFoods(name)

        call.enqueue(object : Callback<List<FoodModel>> {
            override fun onResponse(call: Call<List<FoodModel>>, response: Response<List<FoodModel>>) {
                if (response.code() == AppConstants.HTTP.SUCCESS) {
                    response.body()?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    listener.onFailure("Error at: FoodRepository, searchFoods(), onResponse")
                }
            }

            override fun onFailure(call: Call<List<FoodModel>>, t: Throwable) {
                listener.onFailure("Error at: FoodRepository, searchFoods(), onFailure")
            }
        })
    }
}