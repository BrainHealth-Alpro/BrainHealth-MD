package com.example.brainhealth.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainhealth.di.PredictResponse
import com.example.brainhealth.di.repository.ProgramRepository
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

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun setUploadResponseNull() {
        _uploadResponse.value = null
    }

    fun uploadImage(file: File) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.postResult(file)
                _uploadResponse.value = response
                _isLoading.value = false
            } catch (e: HttpException) {
                //
            }
        }
    }
}