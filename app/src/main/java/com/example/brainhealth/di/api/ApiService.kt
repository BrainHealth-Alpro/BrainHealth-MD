package com.example.brainhealth.di.api

import com.example.brainhealth.di.PredictResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun postResult(
        @Part file: MultipartBody.Part,
    ): PredictResponse
}