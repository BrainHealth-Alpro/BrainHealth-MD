package com.example.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("message")
	val message: Message? = null
)

data class Message(

	@field:SerializedName("message")
	val message: String? = null
)
