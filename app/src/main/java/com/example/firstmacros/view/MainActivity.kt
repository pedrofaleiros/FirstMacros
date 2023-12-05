package com.example.firstmacros.view

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firstmacros.R
import com.example.firstmacros.databinding.ActivityMainBinding
import com.example.firstmacros.service.AppConstants
import com.example.firstmacros.service.repository.local.UserSharedPreferences
import com.example.firstmacros.viewmodel.LoginViewModel
import com.example.firstmacros.viewmodel.MainViewModel
import com.example.firstmacros.viewmodel.MealsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContentView(viewBinding.root)

        viewBinding.bottomNavBar.setOnItemSelectedListener {
            if (viewModel.getBottomNavBarId() != it.itemId) {
                replaceFrameById(it.itemId)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        selectFrame()
    }

    private fun selectFrame() {
        val id = viewModel.getBottomNavBarId()
        if (id != null) {
            replaceFrameById(id)
            viewBinding.bottomNavBar.selectedItemId = id
        } else {
            viewBinding.bottomNavBar.selectedItemId = R.id.home
            replaceFrameById(viewBinding.bottomNavBar.selectedItemId)
        }
    }

    private fun replaceFrameById(id: Int) {
        when (id) {
            R.id.settings -> replaceFrame(SettingsFragment(), id)
            R.id.home -> replaceFrame(HomeFragment(), id)
            R.id.foods -> replaceFrame(FoodsFragment(), id)
            else -> replaceFrame(HomeFragment(), R.id.home)
        }
    }

    private fun replaceFrame(fragment: Fragment, id: Int) {
        viewModel.setBottomNavBarId(id)
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, fragment).commit()
    }
}