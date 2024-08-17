package com.example.brainhealth.di.repository

import com.example.brainhealth.di.db.PredictResponse
import com.example.brainhealth.di.api.ApiService
import com.example.brainhealth.di.db.HistoryResponse
import com.example.brainhealth.di.db.LoginRequest
import com.example.brainhealth.di.db.LoginResponse
import com.example.brainhealth.di.db.ProfileResponse
import com.example.brainhealth.di.db.RegisterRequest
import com.example.brainhealth.di.db.RegisterResponse
import com.example.brainhealth.di.db.TokenManager
import com.example.brainhealth.di.db.UserModel
import com.example.brainhealth.di.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProgramRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){

    suspend fun getToken() = apiService.getToken()

    suspend fun register(fullName: String, email: String, phoneNum: String, password: String, type: String) : RegisterResponse {
        val token = TokenManager.getToken() ?: ""
        val body = RegisterRequest(fullName, email, phoneNum, password, type)
        return apiService.register(token, body)
    }

    suspend fun login(email: String, password: String) : LoginResponse {
        val token = TokenManager.getToken() ?: ""
        val body = LoginRequest(email, password)
        return apiService.login(token, body)
    }

    suspend fun getHistories(userId: Int): HistoryResponse {
        val token = TokenManager.getToken() ?: ""
        return apiService.getHistories(token, userId)
    }

//    suspend fun updateProfile(id: Int, fullName: String, email: String, phoneNum: String, profileImage: File, birthPlace: String, birthDate: String, password: String, type: String) : ProfileUpdateResponse {
//
//    }

    suspend fun getProfile(userId: Int) : ProfileResponse {
        val token = TokenManager.getToken() ?: ""
        return apiService.getProfile(token, userId)
    }
    suspend fun postResult(file: File, userId: Int, patientName: String) : PredictResponse {
        val resultFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            file.name,
            resultFile
        )
        return apiService.postResult(multipartBody, userId, patientName)
    }

    suspend fun postResultLink(link: String, userId: Int, patientName: String) : PredictResponse {
        return apiService.postResultLink(link, userId, patientName)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
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