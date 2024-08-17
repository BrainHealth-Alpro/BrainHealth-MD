package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("history")
	val history: List<HistoryItem>
)

data class HistoryItem(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("nama_lengkap_pasien")
	val namaLengkapPasien: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("hasil")
	val hasil: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_tumor")
	val jenisTumor: String,

	@field:SerializedName("gambar")
	val gambar: String
)
