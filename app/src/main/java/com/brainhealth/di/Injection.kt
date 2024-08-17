package com.brainhealth.di

import android.content.Context
import com.brainhealth.di.api.ApiConfig
import com.brainhealth.di.pref.UserPreference
import com.brainhealth.di.pref.dataStore
import com.brainhealth.di.repository.ProgramRepository

object Injection {
    fun provideRepository(context: Context): ProgramRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return ProgramRepository.getInstance(pref, apiService)
    }
}