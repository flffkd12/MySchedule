package com.example.myschedule.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.monthlyschedule.ScheduleCard
import com.example.myschedule.ui.theme.*

@Composable
fun TodaySchedule(todaySchedules: List<Schedule>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth().height(300.dp).padding(ContentPadding)
    ) {
        Text(
            text = stringResource(R.string.today_schedule),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge
        )

        if (todaySchedules.isEmpty()) {
            Text(
                text = stringResource(R.string.no_schedule),
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            val colorList = listOf(Red, Orange, LightGreen, Blue)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(todaySchedules) { i, schedule ->
                    ScheduleCard(
                        schedule = schedule,
                        color = colorList[i % colorList.size]
                    )
                }
            }
        }
    }
}