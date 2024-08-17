package com.example.brainhealth.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.brainhealth.di.db.ErrorResponse
import com.example.brainhealth.di.db.HistoryItem
import com.example.brainhealth.di.db.SingleErrorResponse
import com.example.brainhealth.di.db.UserModel
import com.example.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailHistoryViewModel(private val repository: ProgramRepository) : ViewModel() {
    private val _listHistory = MutableLiveData<List<HistoryItem>>()
    val listHistory: LiveData<List<HistoryItem>> = _listHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDanger = MutableLiveData<Boolean>()
    val isDanger: LiveData<Boolean> = _isDanger

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getHistory(userId: Int) {
        _isLoading.value = true
        _isDanger.value = false
        viewModelScope.launch {
            try {
                val response = repository.getHistories(userId)
                _listHistory.value = response.history
                val hasTumor = response.history.any { it.jenisTumor != "Notumor" }
                if (hasTumor) _isDanger.value = true
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getHistorySehat(userId: Int) {
        _isLoading.value = true
        _isDanger.value = false
        viewModelScope.launch {
            try {
                val response = repository.getHistories(userId)
                _listHistory.value = response.history.filter { it.jenisTumor == "Notumor" }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getHistoryTidakSehat(userId: Int) {
        _isLoading.value = true
        _isDanger.value = true
        viewModelScope.launch {
            try {
                val response = repository.getHistories(userId)
                _listHistory.value = response.history.filter { it.jenisTumor != null }
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}