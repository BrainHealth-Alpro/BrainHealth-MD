package com.brainhealth.di.db

data class UserModel (
    val email: String,
    val id: Int,
    val name: String = "",
    val type: String,
    val isLogin: Boolean = false
)