package com.example.myschedule.ui.monthlyschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myschedule.data.local.database.entity.Schedule
import com.example.myschedule.ui.data.Day
import com.example.myschedule.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun DaysInCalendar(
    currentYearMonth: YearMonth,
    clickedDate: LocalDate,
    schedules: List<Schedule>,
    isFirstMonth: Boolean,
    isLastMonth: Boolean,
    onDateClick: (LocalDate) -> Unit
) {

    // 이번달 첫째날 요일
    val firstDayOfWeekInMonth = currentYearMonth.atDay(1).dayOfWeek.value

    // 달력의 n by 7 규격을 맞추기 위해 표현해야하는 저번 달 마지막 날짜의 개수
    val previousMonthDayCnt = firstDayOfWeekInMonth % 7

    // 저번 달
    val previousYearMonth = currentYearMonth.plusMonths(-1)

    // 저번 달의 마지막 날짜
    val lastDayOfMonth = previousYearMonth.atEndOfMonth().dayOfMonth

    // 화면에 표시할 날짜들
    val daysList = mutableListOf<Day>()

    // 달력의 날짜 중 저번 달의 마지막 주 날짜들
    for (i in (lastDayOfMonth - previousMonthDayCnt + 1)..lastDayOfMonth) {
        daysList.add(
            Day(LocalDate.of(previousYearMonth.year, previousYearMonth.month, i), LightGray)
        )
    }

    // 달력의 날짜 중 이번 달의 날짜들
    for (i in 1..currentYearMonth.atEndOfMonth().dayOfMonth) {
        daysList.add(Day(LocalDate.of(currentYearMonth.year, currentYearMonth.month, i), Black))
    }

    // 달력의 날짜 중 다음 달의 첫째 주 날짜들
    if (daysList.size % 7 != 0) {
        val nextYearMonth = currentYearMonth.plusMonths(1)
        for (i in 1..7 - daysList.size % 7) {
            daysList.add(Day(LocalDate.of(nextYearMonth.year, nextYearMonth.month, i), LightGray))
        }
    }

    Column {
        for (weekIndex in daysList.indices.chunked(7)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (index in weekIndex) {
                    val currentDate = daysList[index].date
                    val currentDateColor = daysList[index].color

                    val isClickable = when {
                        isFirstMonth && (currentDate.month < currentYearMonth.month) -> false
                        isLastMonth && (currentDate.month > currentYearMonth.month) -> false
                        else -> true
                    }

                    CalendarDayCell(
                        date = currentDate,
                        dateColor = currentDateColor,
                        isCurrentMonth = currentDateColor == Black,
                        isSelected = clickedDate == currentDate,
                        isClickable = isClickable,
                        schedules = schedules,
                        onDateClick = onDateClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    date: LocalDate,
    dateColor: Color,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    isClickable: Boolean,
    schedules: List<Schedule>,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.aspectRatio(1f).padding(4.dp).clip(CircleShape)
            .background(if (isSelected && isCurrentMonth) SkyBlue else Transparent)
            .clickable(enabled = isClickable) { onDateClick(date) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${date.dayOfMonth}",
                color = when {
                    isSelected -> if (isCurrentMonth) White else dateColor
                    date.dayOfWeek == DayOfWeek.SATURDAY -> if (isCurrentMonth) Blue else LightBlue
                    date.dayOfWeek == DayOfWeek.SUNDAY -> if (isCurrentMonth) Red else LightRed
                    else -> dateColor
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.Center) {
                val colorList = listOf(Red, Orange, LightGreen, Blue)
                val daySchedule = schedules.filter { it.date == date }

                daySchedule.take(colorList.size).forEachIndexed { i, _ ->
                    Box(
                        modifier = Modifier.size(4.dp)
                            .background(colorList[i % colorList.size], CircleShape)
                    )

                    if (i < colorList.size - 1) Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
    }
}
