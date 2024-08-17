package com.example.brainhealth.di.api

import com.example.brainhealth.di.db.HistoryResponse
import com.example.brainhealth.di.db.LoginRequest
import com.example.brainhealth.di.db.LoginResponse
import com.example.brainhealth.di.db.PredictResponse
import com.example.brainhealth.di.db.ProfileResponse
import com.example.brainhealth.di.db.ProfileUpdateResponse
import com.example.brainhealth.di.db.RegisterRequest
import com.example.brainhealth.di.db.RegisterResponse
import com.example.brainhealth.di.db.TokenResponse
import okhttp3.MultipartBody
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
    @GET("csrf")
    suspend fun getToken(): TokenResponse


    @POST("register")
    suspend fun register(
        @Header("X-CSRFToken") token: String,
        @Body body: RegisterRequest
    ) : RegisterResponse

    @POST("login")
    suspend fun login(
        @Header("X-CSRFToken") token: String,
        @Body body: LoginRequest
    ) : LoginResponse

    @GET("history")
    suspend fun getHistories(
        @Header("X-CSRFToken") token: String,
        @Query("user_id") userId: Int
    ) : HistoryResponse

    @FormUrlEncoded
    @POST("profile")
    suspend fun updateProfile(
        @Header("X-CSRFToken") token: String,
        @Field("id") id: Int,
        @Field("nama_lengkap") fullName: String,
        @Field("email") email: String,
        @Field("nomor_telepon") phoneNum: String,
        @Field("foto_profil") profileImage: String,
        @Field("tempat_lahir") birthPlace: String,
        @Field("tanggal_lahir") birthDate: String,
        @Field("kata_sandi") password: String,
        @Field("tipe") type: String
    ) : ProfileUpdateResponse

    @GET("profile")
    suspend fun getProfile(
        @Header("X-CSRFToken") token: String,
        @Query("user_id") userId: Int
    ) : ProfileResponse

    @Multipart
    @POST("predict")
    suspend fun postResult(
        @Part file: MultipartBody.Part,
        @Part("user_id") userId: Int,
        @Part("nama_pasien") patientName: String,
    ): PredictResponse

    @FormUrlEncoded
    @POST("predict/batchLink")
    suspend fun postResultLink(
        @Field("link") link: String,
        @Field("user_id") userId: Int,
        @Field("nama_pasien") patientName: String,
    ): PredictResponse
}