package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myschedule.data.local.database.entity.Schedule
import com.example.myschedule.data.local.model.RegionLocation
import com.example.myschedule.data.local.model.ScheduleTime
import com.example.myschedule.domain.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

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

    suspend fun saveSchedule(
        title: String,
        regionLocation: RegionLocation,
        startTime: ScheduleTime,
        endTime: ScheduleTime,
    ) {
        val scheduleList = mutableListOf<Schedule>()
        val selectedDates = selectedScheduleDates.value

        selectedDates.forEach { selectedDate ->
            scheduleList.add(
                Schedule(
                    date = selectedDate,
                    title = title,
                    regionLocation = regionLocation,
                    startTime = startTime,
                    endTime = endTime,
                )
            )
        }

        scheduleRepository.insertSchedules(scheduleList)

        clearSelectedDate()
    }
}
