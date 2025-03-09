package com.example.myschedule.add_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.Routes
import com.example.myschedule.ui.theme.*

@Composable
fun AddSchedule(scheduleViewModel: ScheduleViewModel, navController: NavController) {
    val selectedDates by scheduleViewModel.selectedScheduleDates.collectAsState()

    Scaffold(
        bottomBar = { BtmNavBar(navController) },
        containerColor = LightGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(ContentPadding).padding(bottom = 52.dp)
            ) {
                Text(
                    text = "날짜를 선택해 주세요",
                    color = Black,
                    style = MaterialTheme.typography.titleLarge
                )
                DateSelectUI(scheduleViewModel, selectedDates)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "선택한 날짜",
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )
                    Button(
                        onClick = { scheduleViewModel.clearSelectedDate() },
                        shape = RoundedAllCornerShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGreen,
                            contentColor = White
                        ),
                        modifier = Modifier.size(84.dp, 34.dp)
                    ) {
                        Text(
                            text = "초기화",
                            color = White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                SelectedDatesList(selectedDates)
            }
            ElevatedButton(
                onClick = { navController.navigate(Routes.MAIN_SCREEN) },
                shape = RoundedAllCornerShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    contentColor = White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
                    .padding(start = ContentPadding, end = ContentPadding, bottom = ContentPadding)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "다음", color = White, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}