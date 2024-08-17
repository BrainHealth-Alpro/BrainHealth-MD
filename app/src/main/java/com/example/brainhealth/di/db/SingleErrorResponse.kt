package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class SingleErrorResponse(

	@field:SerializedName("message")
	val message: String
)
