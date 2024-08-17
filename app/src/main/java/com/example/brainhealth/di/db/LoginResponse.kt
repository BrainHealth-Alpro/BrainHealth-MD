package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String
)
