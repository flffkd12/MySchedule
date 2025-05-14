package com.example.myschedule.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.Blue
import com.example.myschedule.ui.theme.Red

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
