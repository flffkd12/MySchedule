package com.example.myschedule.mainscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.Routes
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.UserViewModel
import com.example.myschedule.viewmodels.WeatherViewModel
import java.time.LocalDate

@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {

    val regionName = weatherViewModel.regionName.collectAsState().value
    val userName = userViewModel.userName.observeAsState().value

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

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(0.85f).fillMaxWidth().clip(RoundedTopCornerShape)
                    .background(White)
            ) {
                val scheduleList by monthlyScheduleViewModel.scheduleList.collectAsState()
                val todaySchedules = scheduleList.filter { it.date == LocalDate.now() }

                TodaySchedule(weatherViewModel, todaySchedules)

                HorizontalDivider(thickness = 4.dp, color = LightGreen)

                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = ContentPadding)
                        .clickable { navController.navigate(Routes.SELECT_REGION_SCREEN) },
                    colors = CardDefaults.cardColors(containerColor = White),
                    border = BorderStroke(1.dp, LightGray),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = regionName,
                            color = if (regionName != "날씨를 확인할 지역 선택") Black else Gray,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Black
                        )
                    }
                }
                // 웨더 뷰모델에서 해당 지역에 대한 정보 가져올 수 있는 메서드 만들기

                // 날씨 UI
            }
        }
    }
}
