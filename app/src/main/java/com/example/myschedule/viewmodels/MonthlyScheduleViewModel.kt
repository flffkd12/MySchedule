package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschedule.data.local.database.entity.Schedule
import com.example.myschedule.domain.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _scheduleList = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleList: StateFlow<List<Schedule>> = _scheduleList.asStateFlow()

    fun fetchScheduleList() {
        viewModelScope.launch(Dispatchers.IO) {
            val schedules = scheduleRepository.getAllSchedules()
            _scheduleList.value = schedules.sortedWith(
                compareBy<Schedule> { it.date }.thenBy { it.startTime.hour }
            )
        }
    }

    suspend fun deleteSchedule(id: Long) {
        scheduleRepository.deleteSchedule(id)
    }
}
