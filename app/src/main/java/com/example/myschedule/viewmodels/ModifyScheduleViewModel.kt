package com.example.myschedule.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myschedule.data.Location
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.data.ScheduleTime
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.database.entity.Schedule

class ModifyScheduleViewModel : ViewModel() {

    private val id = mutableLongStateOf(0L)

    val title = mutableStateOf("")

    val firstRegion = mutableStateOf("")
    val secondRegion = mutableStateOf("")
    val thirdRegion = mutableStateOf("")

    val startTimeAmPm = mutableStateOf("")
    val startTimeHour = mutableIntStateOf(0)
    val startTimeMinute = mutableIntStateOf(0)

    val endTimeAmPm = mutableStateOf("")
    val endTimeHour = mutableIntStateOf(0)
    val endTimeMinute = mutableIntStateOf(0)

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
                startTimeHour.intValue,
                startTimeMinute.intValue
            ),
            ScheduleTime(
                endTimeAmPm.value,
                endTimeHour.intValue,
                endTimeMinute.intValue
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
        startTimeHour.intValue = schedule.startTime.hour
        startTimeMinute.intValue = schedule.startTime.minute
        endTimeAmPm.value = schedule.endTime.amPm
        endTimeHour.intValue = schedule.endTime.hour
        endTimeMinute.intValue = schedule.endTime.minute
    }
}
