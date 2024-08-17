package com.example.brainhealth.di

import android.content.Context
import com.example.brainhealth.di.api.ApiConfig
import com.example.brainhealth.di.pref.UserPreference
import com.example.brainhealth.di.pref.dataStore
import com.example.brainhealth.di.repository.ProgramRepository

object Injection {
    fun provideRepository(context: Context): ProgramRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return ProgramRepository.getInstance(pref, apiService)
    }
}