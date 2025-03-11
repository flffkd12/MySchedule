package com.example.myschedule.add_schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.LightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ScrollTimePicker(
    selectedAmPm: MutableState<String>,
    selectedHour: MutableState<String>,
    selectedMinute: MutableState<String>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val amPmList = listOf("", "오전", "오후", "")
        TimePickerColumn(
            items = amPmList,
            selectedItem = selectedAmPm,
        )

        Spacer(modifier = Modifier.width(32.dp))

        val hourList = listOf("") + (1..12).map { it.toString() } + listOf("")
        TimePickerColumn(
            items = hourList,
            selectedItem = selectedHour,
            isHour = true
        )

        Spacer(modifier = Modifier.width(16.dp))

        val minuteList = listOf("") + (0..59).map { it.toString() } + listOf("")
        TimePickerColumn(
            items = minuteList,
            selectedItem = selectedMinute,
            isMinute = true
        )
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun TimePickerColumn(
    items: List<String>,
    selectedItem: MutableState<String>,
    isHour: Boolean = false,
    isMinute: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = items.indexOf(selectedItem.value) - 1
    )

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.width(if (isHour || isMinute) 24.dp else 32.dp).height(78.dp)
    ) {
        itemsIndexed(items) { index, item ->
            if (index == 0 || index == items.lastIndex) {
                Spacer(modifier = Modifier.height(26.dp))
            } else {
                Text(
                    text = if (isMinute) item.padStart(2, '0') else item,
                    color = if (item == selectedItem.value) Black else LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    LaunchedEffect(lazyListState.firstVisibleItemScrollOffset) {
        val centerIndex = lazyListState.firstVisibleItemIndex + 1

        if (centerIndex > 0 && centerIndex < items.lastIndex) {
            val centerOffset = lazyListState.firstVisibleItemScrollOffset
            if (centerOffset >= 34) {
                selectedItem.value = items[centerIndex + 1]
            } else {
                selectedItem.value = items[centerIndex]
            }
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        coroutineScope.launch(Dispatchers.Main) {
            if (lazyListState.firstVisibleItemScrollOffset >= 34) {
                lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex + 1)
            } else {
                lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
            }
        }
    }
}
// 가운데 행 색칠