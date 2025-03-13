package com.example.myschedule.schedulecreate

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.R
import com.example.myschedule.Routes
import com.example.myschedule.schedulecreate.dateselect.DateSelectUI
import com.example.myschedule.schedulecreate.dateselect.SelectedDatesList
import com.example.myschedule.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateSchedule(createScheduleViewModel: CreateScheduleViewModel, navController: NavController) {
    val selectedDates by createScheduleViewModel.selectedScheduleDates.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.CREATE_SCHEDULE) },
        containerColor = LightGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().padding(ContentPadding).padding(bottom = 52.dp)
            ) {
                Text(
                    text = stringResource(R.string.date_selection_guide),
                    color = Black,
                    style = MaterialTheme.typography.titleLarge
                )
                DateSelectUI(createScheduleViewModel, selectedDates)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.date_selected_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )
                    Button(
                        onClick = { createScheduleViewModel.clearSelectedDate() },
                        shape = RoundedAllCornerShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGreen,
                            contentColor = White
                        ),
                        modifier = Modifier.size(84.dp, 34.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.date_init_name),
                            color = White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                SelectedDatesList(selectedDates)
            }

            ElevatedButton(
                onClick = {
                    if (selectedDates.isEmpty()) {
                        coroutineScope.launch(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                R.string.date_selection_guide,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        navController.navigate(Routes.CREATE_TITLE_AND_TIME)
                    }
                },
                shape = RoundedAllCornerShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    contentColor = White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
                    .padding(start = ContentPadding, end = ContentPadding, bottom = ContentPadding)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(R.string.next),
                    color = White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}