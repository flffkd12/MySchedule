package com.example.myschedule.schedulecreate.dateselect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.Blue
import com.example.myschedule.ui.theme.Red
import com.example.myschedule.viewmodels.CreateScheduleViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateSelectUI(createScheduleViewModel: CreateScheduleViewModel, selectedDates: List<LocalDate>) {

    val displayedMonthNum = 13
    val pagerState = rememberPagerState { displayedMonthNum }

    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val currentYearMonth = remember { YearMonth.now().plusMonths(page.toLong()) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${currentYearMonth.year}년 " + currentYearMonth.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.KOREAN
                ),
                style = MaterialTheme.typography.titleMedium
            )

            DayOfWeek()

            DaysInMonth(
                currentYearMonth = currentYearMonth,
                selectedDates = selectedDates,
                isFirstMonth = page == 0,
                isLastMonth = page == displayedMonthNum - 1,
                onDateClick = { clickedDate ->
                    if (selectedDates.contains(clickedDate)) {
                        createScheduleViewModel.removeSelectedDate(clickedDate)
                    } else {
                        createScheduleViewModel.addSelectedDate(clickedDate)
                    }

                    val clickedDateMonth = clickedDate.year * 12 + clickedDate.monthValue
                    val currentMonth = currentYearMonth.year * 12 + currentYearMonth.monthValue

                    if (clickedDateMonth < currentMonth) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page - 1)
                        }
                    } else if (clickedDateMonth > currentMonth) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DayOfWeek() {
    val dayList = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(modifier = Modifier.fillMaxWidth()) {
        dayList.forEach { day ->
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (day) {
                        "일" -> Red
                        "토" -> Blue
                        else -> Black
                    }
                )
            }
        }
    }
}
