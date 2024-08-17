package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("foto_profil")
	val fotoProfil: String?,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: String?,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("kata_sandi")
	val kataSandi: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tipe")
	val tipe: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String?
)
