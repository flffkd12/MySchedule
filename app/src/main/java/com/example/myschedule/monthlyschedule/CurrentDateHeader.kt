package com.example.myschedule.monthlyschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.Gray
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CurrentDateHeader(scheduleList: List<Schedule>, currentDate: LocalDate) {

    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            Text(
                text = "${currentDate.dayOfMonth}",
                color = Black,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = currentDate.dayOfWeek.getDisplayName(
                    TextStyle.FULL,
                    Locale.KOREAN
                ),
                color = Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        val currentDateSchedules = scheduleList.filter { it.date == currentDate }
        Text(
            text = if (currentDateSchedules.isEmpty()) stringResource(R.string.no_schedule)
            else "현재 일정이 ${currentDateSchedules.size}개 있어요",
            color = Gray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}
