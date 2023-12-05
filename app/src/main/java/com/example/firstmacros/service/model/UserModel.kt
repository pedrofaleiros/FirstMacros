package com.example.firstmacros.service.model

import com.google.gson.annotations.SerializedName

class UserModel {

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("token")
    lateinit var token: String
}