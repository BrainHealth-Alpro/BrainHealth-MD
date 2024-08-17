package com.example.brainhealth.di.api

import com.example.brainhealth.di.db.PredictResponse
import com.example.brainhealth.di.db.TokenResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("api/csrf")
    suspend fun getToken(): TokenResponse

    @Multipart
    @POST("predict")
    suspend fun postResult(
        @Part file: MultipartBody.Part,
    ): PredictResponse
}