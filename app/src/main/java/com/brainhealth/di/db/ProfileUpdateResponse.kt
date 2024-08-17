package com.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class ProfileUpdateResponse(

	@field:SerializedName("message")
	val message: String
)
