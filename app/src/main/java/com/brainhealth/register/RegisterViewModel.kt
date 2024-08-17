package com.brainhealth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brainhealth.Utils.isEmailValid
import com.brainhealth.Utils.isPasswordValid
import com.brainhealth.di.db.ErrorResponse
import com.brainhealth.di.db.RegisterResponse
import com.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val repository: ProgramRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    private val _message = MutableLiveData<String?>()
    val message : LiveData<String?> = _message

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun postData(fullName: String, email: String, phoneNum: String, password: String) {
        _isLoading.value = true

        if (!isEmailValid(email) || !isPasswordValid(password)) {
            _message.value = "Please check the format"
            _isLoading.value = false
            _isError.value = true
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.register(fullName, email, phoneNum, password, "pasien")
                _registerResponse.value = response
                _message.value = response.message
                _isLoading.value = false
                _isError.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _message.value = errorMessage
                _isLoading.value = false
                _isError.value = true
            }
        }
    }
}