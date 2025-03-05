package com.example.myschedule.add_schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.Blue
import com.example.myschedule.ui.theme.ContentPadding
import com.example.myschedule.ui.theme.Red
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateSelectUI() {
    val selectedDates = remember { mutableStateListOf<LocalDate>() }

    val pagerState = rememberPagerState { 13 }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val currentYearMonth = remember { YearMonth.now().plusMonths(page.toLong()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth().padding(ContentPadding)
        ) {
            Text(
                text = "${currentYearMonth.year}년 " + currentYearMonth.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.KOREAN
                ),
                style = MaterialTheme.typography.titleMedium
            )
            DayOfWeek()
            DaysInMonth(currentYearMonth, selectedDates) // 이전 혹은 다음달 일자 누르면 이전으로 가게끔
            // 금일 날짜가 속한 월에서 이전 일자들은 선태갷도 selected에 들어가지 않도록
            //날짜 선택 들어가고, 선택한 것들 부모 컴포저블에 전달
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