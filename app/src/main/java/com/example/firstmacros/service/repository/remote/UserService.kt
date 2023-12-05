package com.example.firstmacros.service.repository.remote

import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.model.LoginUserModel
import com.example.firstmacros.service.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {

    @POST("session-name")
    fun login(@Body request: LoginUserModel): Call<UserModel>
}
