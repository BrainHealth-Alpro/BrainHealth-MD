package com.example.brainhealth.di.repository

import com.example.brainhealth.di.db.PredictResponse
import com.example.brainhealth.di.api.ApiService
import com.example.brainhealth.di.pref.UserPreference
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProgramRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){

    suspend fun getToken() =apiService.getToken()

    suspend fun postResult(file: File) : PredictResponse {
        val resultFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            file.name,
            resultFile
        )
        return apiService.postResult(multipartBody)
    }

    companion object {
        @Volatile
        private var instance: ProgramRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): ProgramRepository =
            instance ?: synchronized(this) {
                instance ?: ProgramRepository(userPreference, apiService)
            }.also { instance = it }
    }
}