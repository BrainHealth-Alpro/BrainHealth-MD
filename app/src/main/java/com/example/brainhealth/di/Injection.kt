package com.example.brainhealth.di

import android.content.Context
import com.example.brainhealth.di.api.ApiConfig
import com.example.brainhealth.di.repository.ProgramRepository

object Injection {
    fun provideRepository(context: Context): ProgramRepository {
        val apiService = ApiConfig.getApiService()
        return ProgramRepository.getInstance(apiService)
    }
}