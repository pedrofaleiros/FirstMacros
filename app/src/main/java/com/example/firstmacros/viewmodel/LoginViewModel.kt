package com.example.firstmacros.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstmacros.R
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.listener.APIListener
import com.example.firstmacros.service.model.UserModel
import com.example.firstmacros.service.model.ValidationModel
import com.example.firstmacros.service.repository.UserRepository
import com.example.firstmacros.service.repository.local.UserSharedPreferences
import com.example.firstmacros.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo = UserRepository()
    private val userSharedPreferences = UserSharedPreferences(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _autoLogin = MutableLiveData<Boolean>()
    val autoLogin: LiveData<Boolean> = _autoLogin

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    fun login(username: String, password: String) {
        if (username == "") {
            _login.value = ValidationModel("Username Invalido")
            return
        }
        if (password == "") {
            _login.value = ValidationModel("Senha invalida")
            return
        }

        userRepo.login(username, password, object : APIListener<UserModel> {
            override fun onSuccess(result: UserModel) {
                Log.d("pedro", "onSuccess ViewModel")
                storeUserData(result)
                RetrofitClient.addBearerToken(result.token)
                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                Log.d("pedro", "onFailure ViewModel")
                _login.value = ValidationModel(message)
            }
        })
    }

    fun tryAutoLogin() {
        val token = userSharedPreferences.get(AppConstants.SHARED.USER_TOKEN)

        if (token == "") {
            _autoLogin.value = false
        } else {
            RetrofitClient.addBearerToken(token)
            _autoLogin.value = true
        }
    }

    fun logoutUser() {
        userSharedPreferences.remove(AppConstants.SHARED.USER_TOKEN)
        userSharedPreferences.remove(AppConstants.SHARED.USER_USERNAME)

        RetrofitClient.addBearerToken("")

        _logout.value = true
    }

    private fun storeUserData(user: UserModel) {
        userSharedPreferences.store(AppConstants.SHARED.USER_TOKEN, user.token)
        userSharedPreferences.store(AppConstants.SHARED.USER_USERNAME, user.name)
    }
}