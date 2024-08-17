package com.example.brainhealth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainhealth.di.db.ErrorResponse
import com.example.brainhealth.di.db.LoginResponse
import com.example.brainhealth.di.db.UserModel
import com.example.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: ProgramRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _message = MutableLiveData<String?>()
    val message : LiveData<String?> = _message

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun login(email: String, password: String, type: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                val saveSessionJob = saveSession(
                    UserModel(
                        email,
                        response.id,
                        response.namaLengkap,
                        type,
                        true
                    )
                )
                saveSessionJob.join()
                _message.value = response.message
                _isLoading.value = false
                _isError.value = false
                _loginResponse.value = response
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message?.message
                _message.value = errorMessage
                _isLoading.value = false
                _isError.value = true
            }

        }

    }

    private fun saveSession(user: UserModel) : Job {
        return viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}