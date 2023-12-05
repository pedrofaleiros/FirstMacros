package com.example.firstmacros.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _bottomNavBarId: Int? = null

    fun setBottomNavBarId(id: Int) {
        _bottomNavBarId = id
    }

    fun getBottomNavBarId(): Int? = _bottomNavBarId
}