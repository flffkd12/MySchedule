package com.example.myschedule.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myschedule.database.MyScheduleDb
import com.example.myschedule.database.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val database: MyScheduleDb) : ViewModel() {
    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    fun setUserEmail(userEmail: String?) {
        _userEmail.value = userEmail
    }

    fun setUserName(userName: String?) {
        _userName.value = userName
    }

    fun saveUser(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().insertUser(User(email))
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val database =
                    MyScheduleDb.getDatabase(checkNotNull(this[APPLICATION_KEY]).applicationContext)
                UserViewModel(database)
            }
        }
    }
}