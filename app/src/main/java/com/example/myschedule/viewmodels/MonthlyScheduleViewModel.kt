package com.example.myschedule.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.database.entity.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MonthlyScheduleViewModel : ViewModel() {

    private val _scheduleList = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleList: StateFlow<List<Schedule>> = _scheduleList.asStateFlow()

    fun fetchScheduleList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val schedules = MyScheduleDb.getDatabase(context).scheduleDao().getAllSchedules()
            _scheduleList.value = schedules.sortedWith(
                compareBy<Schedule> { it.date }.thenBy { it.startTime.hour }
            )
        }
    }

    suspend fun deleteSchedule(context: Context, id: Long) {
        MyScheduleDb.getDatabase(context).scheduleDao().deleteSchedule(id)
    }
}
