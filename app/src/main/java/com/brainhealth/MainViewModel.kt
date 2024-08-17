package com.brainhealth


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.brainhealth.di.db.UserModel
import com.brainhealth.di.repository.ProgramRepository


class MainViewModel(private val repository: ProgramRepository) : ViewModel() {

    init {
//        setCSRFToken()
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
//    private fun setCSRFToken() {
//        viewModelScope.launch {
//            try {
//                val response = repository.getToken()
//                setToken(response.csrfToken)
//            } catch (e: HttpException) {
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//                val errorMessage = errorBody.message
//            }
//        }
//    }
}