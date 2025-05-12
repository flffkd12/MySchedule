package com.example.myschedule.monthlyschedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.R
import com.example.myschedule.Routes
import com.example.myschedule.components.ScheduleCard
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun MonthlySchedule(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController
) {

    val scheduleList by monthlyScheduleViewModel.scheduleList.collectAsState()
    val currentDate = rememberSaveable { mutableStateOf<LocalDate>(LocalDate.now()) }

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.MONTHLY_SCHEDULE) },
        containerColor = LightGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().padding(ContentPadding)
            ) {
                Calendar(currentDate, scheduleList)

                CurrentDateHeader(scheduleList, currentDate.value)

                val currentDateSchedules = scheduleList.filter { it.date == currentDate.value }
                CurrentDateScheduleList(
                    monthlyScheduleViewModel,
                    navController,
                    currentDateSchedules
                )
            }
        }
    }
}

@Composable
fun CurrentDateScheduleList(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController,
    scheduleList: List<Schedule>
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (scheduleList.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.sad),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        val colorList = listOf(Red, Orange, LightGreen, Blue)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(scheduleList) { i, schedule ->
                ScheduleCard(
                    schedule = schedule,
                    color = colorList[i % colorList.size],
                    showOptions = true,
                    onEditClick = {
                        navController.navigate(
                            Routes.MODIFY_SCHEDULE + scheduleToNavArgument(schedule)
                        )
                    },
                    onDeleteClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            monthlyScheduleViewModel.deleteSchedule(
                                context,
                                schedule.id
                            )

                            monthlyScheduleViewModel.fetchScheduleList(context)
                        }
                    }
                )
            }
        }
    }
}

fun scheduleToNavArgument(schedule: Schedule): String {
    val regionNavArg =
        "/${schedule.regionLocation.firstRegion},${schedule.regionLocation.secondRegion},${schedule.regionLocation.thirdRegion}"
    val startTimeNavArg =
        "/${schedule.startTime.amPm},${schedule.startTime.hour},${schedule.startTime.minute}"
    val endTimeNavArg =
        "/${schedule.endTime.amPm},${schedule.endTime.hour},${schedule.endTime.minute}"

    return "/${schedule.id}/${schedule.title}" + regionNavArg + startTimeNavArg + endTimeNavArg
}
