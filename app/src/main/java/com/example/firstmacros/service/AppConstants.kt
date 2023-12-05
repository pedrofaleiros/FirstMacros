package com.example.firstmacros.service

class AppConstants {

    object SHARED {
        const val SHARED_KEY = "userShared"
        const val USER_TOKEN = "userToken"
        const val USER_USERNAME = "userUsername"
    }

    object HTTP {
        const val SUCCESS = 200
    }

    object API {
        private const val IP = "172.30.129.176"
//        private const val IP = "192.168.0.129" // Casa
        const val BASE_URL = "http://$IP:3333/"
    }

    object EXTRAS {
        const val MEAL_NAME = "name"
        const val MEAL_ID = "mealId"
        const val MEAL_HOUR = "mealHour"
        const val MEAL_MINUTES = "mealMinutes"
    }

}