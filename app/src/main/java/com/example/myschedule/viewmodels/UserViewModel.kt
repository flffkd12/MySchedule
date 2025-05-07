package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myschedule.data.database.MyScheduleDb

class UserViewModel(private val database: MyScheduleDb) : ViewModel() {

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
