package com.example.myschedule.monthlyschedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.ui.theme.*

@Composable
fun ScheduleCard(
    schedule: Schedule,
    color: Color,
    showOptions: Boolean = false,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null
) {

    var dropDownExpanded by remember { mutableStateOf(false) }

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
                    text = "${schedule.startTime.amPm} ${schedule.startTime.hour}시 ${
                        schedule.startTime.minute.toString().padStart(2, '0')
                    }분 ~ ${schedule.endTime.amPm} ${schedule.endTime.hour}시 ${
                        schedule.endTime.minute.toString().padStart(2, '0')
                    }분",
                    color = Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showOptions && onEditClick != null && onDeleteClick != null) {
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
    }
}
