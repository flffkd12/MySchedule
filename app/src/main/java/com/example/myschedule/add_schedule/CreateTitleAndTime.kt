package com.example.myschedule.add_schedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.myschedule.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateTitleAndTime(scheduleViewModel: ScheduleViewModel, navController: NavController) {
    val selectedDates by scheduleViewModel.selectedScheduleDates.collectAsState()

    val titleName = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val startTimeAmPm = rememberSaveable { mutableStateOf("오전") }
    val startTimeHour = rememberSaveable { mutableStateOf("6") }
    val startTimeMinute = rememberSaveable { mutableStateOf("0") }
    val endTimeAmPm = rememberSaveable { mutableStateOf("오후") }
    val endTimeHour = rememberSaveable { mutableStateOf("6") }
    val endTimeMinute = rememberSaveable { mutableStateOf("0") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.CREATE_SCHEDULE) },
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
                        if (titleName.value.isEmpty()) {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    R.string.empty_title_guide,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (titleName.value.length > 20) {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    R.string.exceeded_title_guide,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // 디비 저장 + 일정 생성 버튼과 연계
                            navController.navigate(Routes.MONTHLY_SCHEDULE)
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
                        text = stringResource(R.string.schedule_create),
                        color = White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}