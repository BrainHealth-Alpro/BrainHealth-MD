package com.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class PredictLinkRequest(
	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("nama_pasien")
	val namaPasien: String,

	@field:SerializedName("user_id")
	val userId: Int,
)
