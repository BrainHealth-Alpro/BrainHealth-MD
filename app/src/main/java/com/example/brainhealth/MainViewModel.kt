package com.example.brainhealth

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.brainhealth.di.db.ErrorResponse
import com.example.brainhealth.di.db.TokenManager
import com.example.brainhealth.di.db.TokenManager.setToken
import com.example.brainhealth.di.db.UserModel
import com.example.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException


class MainViewModel(private val repository: ProgramRepository) : ViewModel() {

    init {
        setCSRFToken()
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    private fun setCSRFToken() {
        viewModelScope.launch {
            try {
                val response = repository.getToken()
                setToken(response.csrfToken)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
            }
        }
    }
}