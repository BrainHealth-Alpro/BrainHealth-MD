package com.example.brainhealth.di.api

import com.example.brainhealth.di.db.HistoryResponse
import com.example.brainhealth.di.db.LoginRequest
import com.example.brainhealth.di.db.LoginResponse
import com.example.brainhealth.di.db.PredictLinkRequest
import com.example.brainhealth.di.db.PredictResponse
import com.example.brainhealth.di.db.ProfileResponse
import com.example.brainhealth.di.db.ProfileUpdateResponse
import com.example.brainhealth.di.db.RegisterRequest
import com.example.brainhealth.di.db.RegisterResponse
import com.example.brainhealth.di.db.TokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
//    @GET("csrf")
//    suspend fun getToken(): TokenResponse

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ) : RegisterResponse

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ) : LoginResponse

    @Headers("Content-Type: application/json")
    @GET("history")
    suspend fun getHistories(
        @Query("user_id") userId: Int
    ) : HistoryResponse

    @Multipart
    @POST("profile")
    suspend fun updateProfile(
        @Part("id") id: RequestBody,
        @Part("nama_lengkap") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("nomor_telepon") phoneNum: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("tempat_lahir") birthPlace: RequestBody,
        @Part("tanggal_lahir") birthDate: RequestBody,
        @Part("kata_sandi") password: RequestBody,
        @Part("tipe") type: RequestBody
    ) : ProfileUpdateResponse

    @Headers("Content-Type: application/json")
    @GET("profile")
    suspend fun getProfile(
        @Query("user_id") userId: Int
    ) : ProfileResponse

    @Multipart
    @POST("predict")
    suspend fun postResult(
        @Part file: MultipartBody.Part,
        @Part("user_id") userId: RequestBody,
        @Part("nama_pasien") patientName: RequestBody,
    ): PredictResponse

    @Headers("Content-Type: application/json")
    @POST("predict/batchLink")
    suspend fun postResultLink(
        @Body body: PredictLinkRequest
    ): PredictResponse
}