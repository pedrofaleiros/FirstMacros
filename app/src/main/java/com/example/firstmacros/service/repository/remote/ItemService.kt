package com.example.firstmacros.service.repository.remote

import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.request.ItemRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ItemService {

    @POST("item")
    fun createitem(@Body request: ItemRequest): Call<ItemModel>

    @DELETE("item")
    fun deleteItem(@Query("item_id") id: String): Call<ItemModel>

    @PUT("item")
    fun updateItem(@Body request: ItemRequest): Call<ItemModel>
}