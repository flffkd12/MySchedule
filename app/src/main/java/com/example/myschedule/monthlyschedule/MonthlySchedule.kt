package com.example.myschedule.monthlyschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.Routes
import com.example.myschedule.ui.theme.*
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

                SelectedDateHeader(scheduleList, currentDate.value)
            }
        }
    }
}