package com.example.myschedule.monthlyschedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.schedulecreate.dateselect.DayOfWeek
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Calendar(currentDate: MutableState<LocalDate>, scheduleList: List<Schedule>) {

    val displayedMonthNum = 13
    val pagerState = rememberPagerState { displayedMonthNum }

    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val currentYearMonth = remember { YearMonth.now().plusMonths(page.toLong()) }
        val firstEpochDayOfMonth = currentYearMonth.atDay(1).toEpochDay()
        val lastEpochDayOfMonth = currentYearMonth.atEndOfMonth().toEpochDay()
        val currentMonthScheduleList = scheduleList.filter { schedule ->
            val scheduleEpochDay = schedule.date.toEpochDay()
            scheduleEpochDay in firstEpochDayOfMonth..lastEpochDayOfMonth
        }

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

            DaysInCalendar(
                currentYearMonth = currentYearMonth,
                clickedDate = currentDate.value,
                schedules = currentMonthScheduleList,
                isFirstMonth = page == 0,
                isLastMonth = page == displayedMonthNum - 1,
                onDateClick = { clickedDate ->
                    currentDate.value = clickedDate

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
