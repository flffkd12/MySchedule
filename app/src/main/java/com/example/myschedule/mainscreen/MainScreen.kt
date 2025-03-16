package com.example.myschedule.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.Routes
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel
import com.example.myschedule.ui.theme.DefaultHorizontalPadding
import com.example.myschedule.ui.theme.LightGreen
import com.example.myschedule.ui.theme.RoundedTopCornerShape
import com.example.myschedule.ui.theme.White
import java.time.LocalDate

@Composable
fun MainScreen(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController,
    userName: String?
) {
    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.MAIN_SCREEN) },
        containerColor = LightGreen
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(horizontal = DefaultHorizontalPadding)
        ) {
            Box(modifier = Modifier.weight(0.15f).fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(Modifier.weight(1f))
                    Text("${userName}님 반갑습니다", color = White)
                }
            }
            Box(
                modifier = Modifier.weight(0.85f).fillMaxWidth().clip(RoundedTopCornerShape)
                    .background(White)
            ) {
                val scheduleList by monthlyScheduleViewModel.scheduleList.collectAsState()
                val todaySchedules = scheduleList.filter { it.date == LocalDate.now() }
                TodaySchedule(todaySchedules)
            }
        }
    }
}