package com.example.myschedule.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.data.WeatherCode
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.width(48.dp).fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.star),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = schedule.title,
                    color = Black,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = scheduleTimeRange(schedule.startTime, schedule.endTime),
                    color = Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (showWeather || showOptions) {
                Spacer(modifier = Modifier.weight(1f))

                WeatherOrOption(showWeather, weatherCode, showOptions, onEditClick, onDeleteClick)
            }
        }
    }
}

fun scheduleTimeRange(startTime: ScheduleTime, endTime: ScheduleTime): String {
    return "${startTime.amPm} ${startTime.hour}시 ${startTime.minute.toString().padStart(2, '0')}분" +
            " ~ " +
            "${endTime.amPm} ${endTime.hour}시 ${endTime.minute.toString().padStart(2, '0')}분"
}

@Composable
fun WeatherOrOption(
    showWeather: Boolean,
    weatherCode: WeatherCode?,
    showOptions: Boolean,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null
) {

    var dropDownExpanded by remember { mutableStateOf(false) }

    if (showWeather) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxSize().padding(end = 8.dp)
        ) {
            when (weatherCode) {
                WeatherCode.SUNNY -> Image(
                    painter = painterResource(R.drawable.sunny),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                WeatherCode.SUNNY_WITH_CLOUD -> Image(
                    painter = painterResource(R.drawable.little_cloudy),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                WeatherCode.CLOUDY -> Image(
                    painter = painterResource(R.drawable.cloudy),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                WeatherCode.RAINY -> Image(
                    painter = painterResource(R.drawable.rainy),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                WeatherCode.SNOWY -> Image(
                    painter = painterResource(R.drawable.snowy),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                else -> Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    } else if (showOptions && onEditClick != null && onDeleteClick != null) {
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
                DropdownMenuItem(
                    onClick = {
                        onEditClick()
                        dropDownExpanded = false
                    },
                    text = { Text("수정") }
                )

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