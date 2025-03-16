package com.example.myschedule.monthlyschedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.R
import com.example.myschedule.Routes
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTitle
import com.example.myschedule.schedulecreate.titletimeinput.ScrollTimePicker
import com.example.myschedule.schedulecreate.titletimeinput.TimeCalc
import com.example.myschedule.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ModifySchedule(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController,
    id: Long,
    title: String,
    startTimeElement: List<String>,
    endTimeElement: List<String>
) {
    val titleName = rememberSaveable { mutableStateOf(title) }
    val focusManager = LocalFocusManager.current

    val startTimeAmPm = rememberSaveable { mutableStateOf(startTimeElement[0]) }
    val startTimeHour = rememberSaveable { mutableStateOf(startTimeElement[1]) }
    val startTimeMinute = rememberSaveable { mutableStateOf(startTimeElement[2]) }
    val endTimeAmPm = rememberSaveable { mutableStateOf(endTimeElement[0]) }
    val endTimeHour = rememberSaveable { mutableStateOf(endTimeElement[1]) }
    val endTimeMinute = rememberSaveable { mutableStateOf(endTimeElement[2]) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.MONTHLY_SCHEDULE) },
        containerColor = LightGreen,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { focusManager.clearFocus() }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize().padding(ContentPadding)
            ) {
                ScheduleTitle(titleName)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.schedule_start_time),
                    style = MaterialTheme.typography.titleMedium
                )
                ScrollTimePicker(startTimeAmPm, startTimeHour, startTimeMinute)

                Text(
                    text = stringResource(R.string.schedule_end_time),
                    style = MaterialTheme.typography.titleMedium
                )
                ScrollTimePicker(endTimeAmPm, endTimeHour, endTimeMinute)

                Spacer(modifier = Modifier.weight(1f))

                ElevatedButton(
                    onClick = {
                        val scheduleStart = TimeCalc(
                            startTimeAmPm.value,
                            startTimeHour.value.toInt(),
                            startTimeMinute.value.toInt()
                        )
                        val scheduleEnd = TimeCalc(
                            endTimeAmPm.value,
                            endTimeHour.value.toInt(),
                            endTimeMinute.value.toInt()
                        )
                        val isScheduleTimeAvailable = scheduleStart < scheduleEnd

                        if (titleName.value.isEmpty()) {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    R.string.empty_title_message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (titleName.value.length > 20) {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    R.string.exceeded_title_message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (!isScheduleTimeAvailable) {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    R.string.unavailable_time_message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            val startTime = ScheduleTime(
                                amPm = startTimeAmPm.value,
                                hour = startTimeHour.value.toInt(),
                                minute = startTimeMinute.value.toInt()
                            )
                            val endTime = ScheduleTime(
                                amPm = endTimeAmPm.value,
                                hour = endTimeHour.value.toInt(),
                                minute = endTimeMinute.value.toInt()
                            )
                            coroutineScope.launch(Dispatchers.IO) {
                                monthlyScheduleViewModel.modifySchedule(
                                    context,
                                    id,
                                    titleName.value,
                                    startTime,
                                    endTime
                                )

                                monthlyScheduleViewModel.fetchScheduleList(context)
                            }

                            navController.popBackStack()
                        }
                    },
                    shape = RoundedAllCornerShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.schedule_modify),
                        color = White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}