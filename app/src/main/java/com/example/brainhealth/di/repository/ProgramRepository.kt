package com.example.brainhealth.di.repository

import com.example.brainhealth.di.PredictResponse
import com.example.brainhealth.di.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProgramRepository private constructor(
    private val apiService: ApiService
){

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
            apiService: ApiService
        ): ProgramRepository =
            instance ?: synchronized(this) {
                instance ?: ProgramRepository(apiService)
            }.also { instance = it }
    }
}