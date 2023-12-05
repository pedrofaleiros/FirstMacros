package com.example.firstmacros.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.firstmacros.R
import com.example.firstmacros.databinding.ActivityLoginBinding
import com.example.firstmacros.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setContentView(viewBinding.root)

        viewBinding.buttonLogin.setOnClickListener {
            val username = viewBinding.editUsername.text.toString()
            val password = viewBinding.editPassword.text.toString()

            viewModel.login(username, password)
        }

        viewModel.tryAutoLogin()

        observe()
    }

    private fun observe() {
        viewModel.login.observe(this) { res ->
            if (res.status()) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Snackbar.make(viewBinding.root, res.message(), Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.autoLogin.observe(this) { res ->
            if (res) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }
}