package com.example.firstmacros.service.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.example.firstmacros.service.AppConstants

class UserSharedPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        AppConstants.SHARED.SHARED_KEY,
        Context.MODE_PRIVATE,
    )

    fun store(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun get(key: String): String {
        return preferences.getString(key, "") ?: ""
    }
}