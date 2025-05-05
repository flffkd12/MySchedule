package com.example.myschedule.mainscreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.Routes
import com.example.myschedule.data.WeatherDto
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.UserViewModel
import com.example.myschedule.viewmodels.WeatherViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {

    val regionName = weatherViewModel.regionName.collectAsState().value
    val regionLocation = weatherViewModel.regionLocation.collectAsState().value
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
                    shape = RoundedAllCornerShape
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

                if (regionLocation != null) {
                    val weatherList by produceState<List<WeatherDto>?>(
                        initialValue = null,
                        regionLocation
                    ) { value = weatherViewModel.getRegionWeatherInfo(regionLocation) }

                    if (weatherList != null) {
                        Log.d("MainScreen", weatherList.toString())
                        RegionWeatherUI(weatherList!!)
                    }
                }
            }
        }
    }
}

@Composable
fun RegionWeatherUI(weatherList: List<WeatherDto>) {

    val now = LocalDateTime.now()
    val todayDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHH"))
    val todayDate = now.toLocalDate()

    fun getKoreanWeekday(date: String): String {
        val d = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
        val weekdays = listOf("일", "월", "화", "수", "목", "금", "토")
        return weekdays[d.dayOfWeek.value % 7]
    }

    Card(
        shape = RoundedAllCornerShape,
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, LightGray),
        modifier = Modifier.fillMaxWidth().padding(horizontal = ContentPadding)
    ) {
        LazyRow {
            items(weatherList) { weatherDto ->
                val date = weatherDto.date
                val hour = weatherDto.time.substring(0, 2)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp).width(48.dp)
                ) {
                    // 1행: 날짜 라벨
                    val isCurrentHour = todayDateTime == date + hour
                    val label = when {
                        isCurrentHour -> "오늘(${getKoreanWeekday(date)})"

                        hour == "00" -> {
                            val baseDate = LocalDate.parse(
                                date,
                                DateTimeFormatter.ofPattern("yyyyMMdd")
                            )
                            when ((baseDate.toEpochDay() - todayDate.toEpochDay()).toInt()) {
                                1 -> "내일(${getKoreanWeekday(date)})"
                                2 -> "모레(${getKoreanWeekday(date)})"
                                else -> ""
                            }
                        }

                        else -> ""
                    }

                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = Black
                    )

                    // 2행: 시간 라벨
                    val timeLabel = if (isCurrentHour) "지금" else "${hour}시"
                    Text(
                        text = timeLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black
                    )
                }
            }
        }
    }
}
