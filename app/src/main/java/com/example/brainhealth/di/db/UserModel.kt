package com.example.brainhealth.di.db

data class UserModel (
    val email: String,
    val id: Int,
    val isLogin: Boolean = false
)