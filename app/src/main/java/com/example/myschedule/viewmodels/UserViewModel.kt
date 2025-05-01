package com.example.myschedule.viewmodels

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.database.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val database: MyScheduleDb) : ViewModel() {

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _userName = MutableLiveData<String?>(null)
    val userName: LiveData<String?> = _userName

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
