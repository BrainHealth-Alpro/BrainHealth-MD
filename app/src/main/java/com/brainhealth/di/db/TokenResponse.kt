package com.brainhealth.di.db

import com.google.gson.annotations.SerializedName

data class TokenResponse(

	@field:SerializedName("csrf_token")
	val csrfToken: String
)
