package com.example.firstmacros.service.repository.remote

import com.example.firstmacros.service.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var instance: Retrofit
        private var token: String = ""

        private fun getInstance(): Retrofit {
            if (!::instance.isInitialized) {
                synchronized(RetrofitClient::class) {
                    val httpClient = OkHttpClient.Builder()

                    httpClient.addInterceptor(bearerInterceptor())

                    instance = Retrofit.Builder()
                        .baseUrl(AppConstants.API.BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
            }
            return instance
        }

        fun addBearerToken(newToken: String) {
            token = newToken
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return getInstance().create(serviceClass)
        }

        private fun bearerInterceptor(): Interceptor {
            return object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder().header("Authorization", "Bearer $token").build()
                    return chain.proceed(request)
                }
            }
        }
    }
}