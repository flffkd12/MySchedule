package com.example.myschedule.add_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.ui.theme.*
import java.time.LocalDate

@Composable
fun AddSchedule(navController: NavController) {
    val selectedDates = remember { mutableStateListOf<LocalDate>() }

    Scaffold(
        bottomBar = { BtmNavBar(navController) },
        containerColor = LightGreen
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(ContentPadding)
            ) {
                Text(
                    text = "날짜를 선택해 주세요",
                    style = MaterialTheme.typography.titleLarge,
                    color = Black
                )
                DateSelectUI(selectedDates)
                Text(text = "선택한 날짜", style = MaterialTheme.typography.titleMedium, color = Black)
                // 중복 선택한 날짜 전부 나열
                // 다음 버튼
            }
        }
    }
}