package com.example.myschedule.schedulecreate

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.database.MyScheduleDb
import com.example.myschedule.database.entity.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun saveSchedule(
        context: Context,
        userEmail: String,
        title: String,
        startTime: ScheduleTime,
        endTime: ScheduleTime,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedDates = selectedScheduleDates.value

            selectedDates.forEach { selectedDate ->
                val schedule = Schedule(
                    userEmail = userEmail,
                    date = selectedDate,
                    title = title,
                    startTime = startTime,
                    endTime = endTime,
                )

                MyScheduleDb.getDatabase(context).scheduleDao().insertScheduleIfNotExist(schedule)
            }
        }
    }
}