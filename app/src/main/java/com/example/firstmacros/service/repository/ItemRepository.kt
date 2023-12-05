package com.example.firstmacros.service.repository

import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.ItemModel
import com.example.firstmacros.service.model.request.ItemRequest
import com.example.firstmacros.service.repository.remote.ItemService
import com.example.firstmacros.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemRepository {
    private val remote = RetrofitClient.getService(ItemService::class.java)

    fun createItem(item: ItemRequest, listener: APIListener<ItemModel>) {
        val call = remote.createitem(item)

        call.enqueue(object : Callback<ItemModel> {
            override fun onResponse(call: Call<ItemModel>, response: Response<ItemModel>) {
                try {
                    if (response.code() == AppConstants.HTTP.SUCCESS) {
                        response.body()?.let { listener.onSuccess(it) }
                    } else {
                        listener.onFailure("Error at: ItemRepository, createItem(), onSuccess")
                    }
                } catch (e: Exception) {
                    listener.onFailure("Error at:  ItemRepository, createItem(), onSuccess catch")
                }
            }

            override fun onFailure(call: Call<ItemModel>, t: Throwable) {
                listener.onFailure("Error at:  ItemRepository, createItem(), onFailure")
            }
        })
    }

    fun deleteItem(id: String, listener: APIListener<ItemModel>) {
        val call = remote.deleteItem(id)

        call.enqueue(object : Callback<ItemModel> {
            override fun onResponse(call: Call<ItemModel>, response: Response<ItemModel>) {
                try {
                    if (response.code() == AppConstants.HTTP.SUCCESS) {
                        response.body()?.let { listener.onSuccess(it) }
                    } else {
                        listener.onFailure("Error at: ItemRepository, deleteItem(), onSuccess")
                    }
                } catch (e: Exception) {
                    listener.onFailure("Error at:  ItemRepository, deleteItem(), onSuccess catch")
                }
            }

            override fun onFailure(call: Call<ItemModel>, t: Throwable) {
                listener.onFailure("Error at:  ItemRepository, deleteItem(), onFailure")
            }
        })
    }
}