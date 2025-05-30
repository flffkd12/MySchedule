package com.example.myschedule.ui.mainscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.components.AppScreenContainer
import com.example.myschedule.data.remote.model.WeatherCode
import com.example.myschedule.data.remote.model.WeatherDto
import com.example.myschedule.data.remote.model.toWeatherCode
import com.example.myschedule.ui.navigation.Routes
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.WeatherViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {

    AppScreenContainer(navController, Routes.MAIN_SCREEN) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val scheduleList by monthlyScheduleViewModel.scheduleList.collectAsState()
            val todaySchedules = scheduleList.filter { it.date == LocalDate.now() }

            TodaySchedule(weatherViewModel, todaySchedules)

            HorizontalDivider(thickness = 4.dp, color = LightGreen)

            Card(
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, LightGray),
                shape = RoundedAllCornerShape,
                modifier = Modifier.fillMaxWidth().padding(horizontal = ContentPadding)
                    .clickable { navController.navigate(Routes.SELECT_REGION_SCREEN) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val regionName = weatherViewModel.regionName.collectAsState().value
                    Text(
                        text = regionName
                            ?: stringResource(R.string.select_region_initial_guide),
                        color = if (regionName != null) Black else Gray,
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

            val regionLocation = weatherViewModel.regionLocation.collectAsState().value
            if (regionLocation != null) {
                val weatherList by produceState<List<WeatherDto>?>(
                    initialValue = null,
                    regionLocation
                ) { value = weatherViewModel.getRegionWeatherInfo(regionLocation) }

                if (weatherList != null) {
                    RegionWeatherUI(weatherList!!)
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
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp).width(48.dp)
                ) {
                    // 1행: 날짜 라벨
                    val isCurrentHour = todayDateTime == date + hour
                    val koreanWeekday = getKoreanWeekday(date)
                    val label = when {
                        isCurrentHour -> stringResource(R.string.weather_label_today, koreanWeekday)

                        hour == "00" -> {
                            val baseDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
                            when ((baseDate.toEpochDay() - todayDate.toEpochDay()).toInt()) {
                                1 -> stringResource(R.string.weather_label_tomorrow, koreanWeekday)

                                2 -> stringResource(
                                    R.string.weather_label_day_after_tomorrow,
                                    koreanWeekday
                                )

                                else -> ""
                            }
                        }

                        else -> ""
                    }

                    Text(
                        text = label,
                        color = Blue,
                        style = MaterialTheme.typography.bodySmall
                    )

                    // 2행: 시간 라벨
                    val timeLabel = if (isCurrentHour) stringResource(R.string.now)
                    else stringResource(R.string.weather_label_hour, hour)

                    Text(
                        text = timeLabel,
                        color = Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // 3행: 날씨 표시
                    when (weatherDto.toWeatherCode()) {
                        WeatherCode.SUNNY -> Image(
                            painter = painterResource(R.drawable.sunny),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        WeatherCode.SUNNY_WITH_CLOUD -> Image(
                            painter = painterResource(R.drawable.little_cloudy),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        WeatherCode.CLOUDY -> Image(
                            painter = painterResource(R.drawable.cloudy),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        WeatherCode.RAINY -> Image(
                            painter = painterResource(R.drawable.rainy),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        WeatherCode.SNOWY -> Image(
                            painter = painterResource(R.drawable.snowy),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        else -> Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // 4행: 기온 표시
                    Text(
                        text = stringResource(R.string.unit_temperature, weatherDto.temperature),
                        color = Black,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    // 5행: 강수, 적설 확률
                    Text(
                        text = stringResource(R.string.unit_percentage, weatherDto.rainProbability),
                        color = if (weatherDto.rainProbability.toInt() >= 60) Blue else Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    // 6행: 강수량, 적설량
                    val isRainy = weatherDto.rainAmount != stringResource(R.string.no_rain)
                    val isSnowy = weatherDto.snowAmount != stringResource(R.string.no_snow)

                    Text(
                        text = if (!isRainy && !isSnowy) ""
                        else if (isRainy) weatherDto.rainAmount.split(" ")[0]
                        else weatherDto.snowAmount,
                        color = if (isRainy || isSnowy) Blue else Transparent,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.background(VeryLightBLue)
                    )
                }
            }
        }
    }
}

@Composable
fun getKoreanWeekday(date: String): String {
    val weekdays = LocalContext.current.resources.getStringArray(R.array.korean_weekdays_short)
    return weekdays[LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).dayOfWeek.value % 7]
}
