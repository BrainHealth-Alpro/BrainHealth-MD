package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String,

	@field:SerializedName("kata_sandi")
	val kataSandi: String,

	@field:SerializedName("tipe")
	val tipe: String,

)
