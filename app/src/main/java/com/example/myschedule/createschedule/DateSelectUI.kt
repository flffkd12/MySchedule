package com.example.myschedule.createschedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.components.DayOfWeek
import com.example.myschedule.viewmodels.CreateScheduleViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun DateSelectUI(createScheduleViewModel: CreateScheduleViewModel, selectedDates: List<LocalDate>) {

    val displayedMonthNum = 13
    val pagerState = rememberPagerState { displayedMonthNum }

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
                text = stringResource(
                    R.string.year_month_numeric_format,
                    currentYearMonth.year,
                    currentYearMonth.month.value
                ),
                style = MaterialTheme.typography.titleMedium
            )

            DayOfWeek()

            val coroutineScope = rememberCoroutineScope()
            DateInMonth(
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
