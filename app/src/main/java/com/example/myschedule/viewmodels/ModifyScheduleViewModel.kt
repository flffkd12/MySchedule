package com.example.myschedule.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myschedule.data.Location
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime

class ModifyScheduleViewModel : ViewModel() {

    private val id = mutableLongStateOf(0L)

    val title = mutableStateOf("")

    val firstRegion = mutableStateOf("")
    val secondRegion = mutableStateOf("")
    val thirdRegion = mutableStateOf("")

    val startTimeAmPm = mutableStateOf("")
    val startTimeHour = mutableStateOf("")
    val startTimeMinute = mutableStateOf("")

    val endTimeAmPm = mutableStateOf("")
    val endTimeHour = mutableStateOf("")
    val endTimeMinute = mutableStateOf("")

    suspend fun modifySchedule(location: Location, context: Context) {
        MyScheduleDb.getDatabase(context).scheduleDao().modifySchedules(
            id.longValue,
            title.value,
            RegionLocation(
                firstRegion.value,
                secondRegion.value,
                thirdRegion.value,
                location
            ),
            ScheduleTime(
                startTimeAmPm.value,
                startTimeHour.value.toInt(),
                startTimeMinute.value.toInt()
            ),
            ScheduleTime(
                endTimeAmPm.value,
                endTimeHour.value.toInt(),
                endTimeMinute.value.toInt()
            ),
        )
    }

    fun setScheduleValues(schedule: Schedule) {
        id.longValue = schedule.id
        title.value = schedule.title
        firstRegion.value = schedule.regionLocation.firstRegion
        secondRegion.value = schedule.regionLocation.secondRegion
        thirdRegion.value = schedule.regionLocation.thirdRegion
        startTimeAmPm.value = schedule.startTime.amPm
        startTimeHour.value = schedule.startTime.hour.toString()
        startTimeMinute.value = schedule.startTime.minute.toString()
        endTimeAmPm.value = schedule.endTime.amPm
        endTimeHour.value = schedule.endTime.hour.toString()
        endTimeMinute.value = schedule.endTime.minute.toString()
    }
}
