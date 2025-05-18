package com.example.myschedule.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myschedule.R
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.Blue
import com.example.myschedule.ui.theme.Red

@Composable
fun DayOfWeek() {
    
    Row(modifier = Modifier.fillMaxWidth()) {
        val dayList = LocalContext.current.resources.getStringArray(R.array.korean_weekdays_short)
        dayList.forEach { day ->
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (day) {
                        dayList[0] -> Red
                        dayList[6] -> Blue
                        else -> Black
                    }
                )
            }
        }
    }
}
