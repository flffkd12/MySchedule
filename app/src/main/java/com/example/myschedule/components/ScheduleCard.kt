package com.example.myschedule.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.data.WeatherCode
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.ui.theme.*

@Composable
fun ScheduleCard(
    schedule: Schedule,
    color: Color,
    showWeather: Boolean = false,
    weatherCode: WeatherCode? = null,
    showOptions: Boolean = false,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null
) {

    Card(
        colors = CardDefaults.cardColors(White),
        border = BorderStroke(1.dp, LightGray),
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column {
                Text(
                    text = schedule.title,
                    color = Black,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = stringResource(
                        id = R.string.schedule_time_range,
                        schedule.startTime.amPm, schedule.startTime.hour, schedule.startTime.minute,
                        schedule.endTime.amPm, schedule.endTime.hour, schedule.endTime.minute
                    ),
                    color = Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showWeather) {
                WeatherImage(weatherCode)
            } else if (showOptions) {
                OptionIcon(onEditClick, onDeleteClick)
            }
        }
    }
}

@Composable
fun WeatherImage(weatherCode: WeatherCode?) {

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.fillMaxSize().padding(end = 8.dp)
    ) {
        val imgResId = when (weatherCode) {
            WeatherCode.SUNNY -> R.drawable.sunny
            WeatherCode.SUNNY_WITH_CLOUD -> R.drawable.little_cloudy
            WeatherCode.CLOUDY -> R.drawable.cloudy
            WeatherCode.RAINY -> R.drawable.rainy
            WeatherCode.SNOWY -> R.drawable.snowy
            else -> R.drawable.close
        }

        Image(
            painter = painterResource(imgResId),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun OptionIcon(onEditClick: (() -> Unit)? = null, onDeleteClick: (() -> Unit)? = null) {

    var dropDownExpanded by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.CenterEnd) {
        IconButton(onClick = { dropDownExpanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Black
            )
        }

        DropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = { dropDownExpanded = false },
            shape = RoundedAllCornerShape,
            containerColor = White,
        ) {
            if (onEditClick != null) {
                DropdownMenuItem(
                    onClick = {
                        onEditClick()
                        dropDownExpanded = false
                    },
                    text = { Text("수정") }
                )
            }

            if (onDeleteClick != null) {
                DropdownMenuItem(
                    onClick = {
                        onDeleteClick()
                        dropDownExpanded = false
                    },
                    text = { Text("삭제") }
                )
            }
        }
    }
}
