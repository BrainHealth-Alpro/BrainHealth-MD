package com.brainhealth.di.db

object TokenManager {
    private var token: String? = null

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String? {
        return token
    }
}