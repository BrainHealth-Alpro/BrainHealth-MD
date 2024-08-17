package com.brainhealth.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.brainhealth.di.db.PredictResponse
import com.brainhealth.di.db.SingleErrorResponse
import com.brainhealth.di.db.UserModel
import com.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class HomeViewModel(private val repository: ProgramRepository) : ViewModel() {
    private val _uploadResponse = MutableLiveData<PredictResponse?>()
    val uploadResponse: LiveData<PredictResponse?> = _uploadResponse

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun setUploadResponseNull() {
        _uploadResponse.value = null
    }

    fun uploadImage(file: File, userId: Int, patientName: String) {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                val response = repository.postResult(file, userId, patientName)
                _uploadResponse.value = response
                _isLoading.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
                _isError.value = true
            }
        }
    }

    fun uploadLink(link: String, userId: Int, patientName: String) {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            try {
                val response = repository.postResultLink(link, userId, patientName)
                _uploadResponse.value = response
                _isLoading.value = false
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
                _message.value = errorMessage
                _isError.value = true
            }
        }
    }
}