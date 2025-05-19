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
import com.example.myschedule.R
import com.example.myschedule.components.ScheduleCard
import com.example.myschedule.components.bottomnav.BtmNavBar
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.ui.navigation.Routes
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.ModifyScheduleViewModel
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun MonthlySchedule(
    modifyScheduleViewModel: ModifyScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController
) {

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.MONTHLY_SCHEDULE) },
        containerColor = LightGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            val scheduleList by monthlyScheduleViewModel.scheduleList.collectAsState()
            val currentDate = rememberSaveable { mutableStateOf<LocalDate>(LocalDate.now()) }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().padding(ContentPadding)
            ) {
                Calendar(currentDate, scheduleList)

                CurrentDateHeader(scheduleList, currentDate.value)

                val currentDateSchedules = scheduleList.filter { it.date == currentDate.value }
                CurrentDateScheduleList(
                    modifyScheduleViewModel,
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
    modifyScheduleViewModel: ModifyScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController,
    scheduleList: List<Schedule>
) {

    if (scheduleList.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.sad),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val colorList = listOf(Red, Orange, LightGreen, Blue)
            itemsIndexed(scheduleList) { i, schedule ->
                ScheduleCard(
                    schedule = schedule,
                    color = colorList[i % colorList.size],
                    showOptions = true,
                    onEditClick = {
                        modifyScheduleViewModel.setScheduleValues(schedule)
                        navController.navigate(Routes.MODIFY_SCHEDULE)
                    },
                    onDeleteClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            monthlyScheduleViewModel.deleteSchedule(context, schedule.id)
                            monthlyScheduleViewModel.fetchScheduleList(context)
                        }
                    }
                )
            }
        }
    }
}

