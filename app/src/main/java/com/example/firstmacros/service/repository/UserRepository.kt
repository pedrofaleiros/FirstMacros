package com.example.firstmacros.service.repository

import android.util.Log
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.LoginUserModel
import com.example.firstmacros.service.model.UserModel
import com.example.firstmacros.service.repository.remote.RetrofitClient
import com.example.firstmacros.service.repository.remote.UserService
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val remote = RetrofitClient.getService(UserService::class.java)

    private fun failResponse(response: Response<UserModel>): String {
        return try {
            val errorBodystr = response.errorBody()?.string()
            if (errorBodystr == null) {
                "Erro desconhecido"
            } else {
                val json = JSONObject(errorBodystr)
                val errorMsg = json.getString("error")
                errorMsg
            }
        } catch (e: Exception) {
            "Erro desconhecido catch"
        }
    }

    fun login(
        username: String,
        password: String,
        listener: APIListener<UserModel>,
    ) {
        val call = remote.login(LoginUserModel(username, password))

        call.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                Log.d("pedro", "onSuccess Repository")
                if (response.code() == AppConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(failResponse(response))
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("pedro", "onFailure Repository")
                listener.onFailure("Error at: User Repository, login(), onFailure ")
            }
        })
    }
}