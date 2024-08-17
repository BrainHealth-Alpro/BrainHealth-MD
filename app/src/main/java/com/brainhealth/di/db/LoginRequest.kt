package com.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("kata_sandi")
	val kataSandi: String,

)
