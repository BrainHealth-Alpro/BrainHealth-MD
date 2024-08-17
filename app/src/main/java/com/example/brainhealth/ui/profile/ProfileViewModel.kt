package com.example.brainhealth.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.brainhealth.di.db.ProfileResponse
import com.example.brainhealth.di.db.ProfileUpdateResponse
import com.example.brainhealth.di.db.SingleErrorResponse
import com.example.brainhealth.di.db.UserModel
import com.example.brainhealth.di.repository.ProgramRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class ProfileViewModel(private val repository: ProgramRepository) : ViewModel() {

    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> = _profile

    private val _profileUpdateResponse = MutableLiveData<ProfileUpdateResponse?>()
    val profileUpdateResponse: LiveData<ProfileUpdateResponse?> = _profileUpdateResponse



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Daftar kota-kota Indonesia
    val indonesianCities = listOf(
        "Ambon", "Aceh", "Balikpapan", "Bandar Lampung", "Bandung", "Banjarmasin", "Batam", "Batu", "Bau-Bau",
        "Bekasi", "Bengkulu", "Binjai", "Bitung", "Bogor", "Cilegon", "Cimahi", "Cirebon", "Denpasar", "Depok",
        "Gorontalo", "Jakarta", "Jambi", "Jayapura", "Kediri", "Kendari", "Lhokseumawe", "Lubuklinggau", "Magelang",
        "Makasar", "Malang", "Malinau", "Manado", "Manokwari", "Mataram", "Medan", "Merauke", "Madiun", "Palu",
        "Parepare", "Pasuruan", "Pekalongan", "Pekanbaru", "Pematangsiantar", "Pontianak", "Probolinggo", "Purbalingga",
        "Purwokerto", "Salatiga", "Samarinda", "Semarang", "Serang", "Sibolga", "Sidoarjo", "Solo", "Sorong", "Sukabumi",
        "Tangerang", "Tanjungbalai", "Tarakan", "Tasikmalaya", "Tegal", "Ternate", "Tidore", "Tomohon", "Tual", "Yogyakarta"
    )


    fun nullProfileUpdate() {
        _profileUpdateResponse.value = null
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getProfile(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getProfile(userId)
                _profile.value = response
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun updateProfile(id: Int, fullName: String, email: String, phoneNum: String, profileImage: File?, birthPlace: String, birthDate: String, password: String?, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updateProfile(id, fullName, email, phoneNum, profileImage, birthPlace, birthDate, null, type)
                _profileUpdateResponse.value = response
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, SingleErrorResponse::class.java)
                val errorMessage = errorBody.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun logout() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.logout()
            _isLoading.value = false
        }
    }
}