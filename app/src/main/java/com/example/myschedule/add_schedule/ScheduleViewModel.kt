package com.example.myschedule.add_schedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class ScheduleViewModel : ViewModel() {
    private val _selectedScheduleDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val selectedScheduleDates: StateFlow<List<LocalDate>> = _selectedScheduleDates.asStateFlow()

    fun addSelectedDate(selectedDate: LocalDate) {
        val currentScheduleDates = _selectedScheduleDates.value.toMutableList()
        currentScheduleDates.add(selectedDate)
        _selectedScheduleDates.value = currentScheduleDates
    }

    fun removeSelectedDate(selectedDate: LocalDate) {
        val currentScheduleDates = _selectedScheduleDates.value.toMutableList()
        currentScheduleDates.remove(selectedDate)
        _selectedScheduleDates.value = currentScheduleDates
    }

    fun clearSelectedDate() {
        _selectedScheduleDates.value = emptyList()
    }
}