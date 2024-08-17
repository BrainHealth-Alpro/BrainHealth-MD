package com.example.brainhealth

import android.content.Context
import android.provider.ContactsContract.Profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brainhealth.di.Injection
import com.example.brainhealth.di.db.ProfileResponse
import com.example.brainhealth.di.repository.ProgramRepository
import com.example.brainhealth.login.LoginViewModel
import com.example.brainhealth.register.RegisterViewModel
import com.example.brainhealth.ui.history.DetailHistoryViewModel
import com.example.brainhealth.ui.history.HistoryViewModel
import com.example.brainhealth.ui.home.HomeViewModel
import com.example.brainhealth.ui.profile.ProfileViewModel

class ViewModelFactory(private val repository: ProgramRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailHistoryViewModel::class.java) -> {
                DetailHistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}