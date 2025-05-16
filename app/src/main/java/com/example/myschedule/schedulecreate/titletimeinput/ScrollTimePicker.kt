package com.example.myschedule.schedulecreate.titletimeinput

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.LightGray
import com.example.myschedule.ui.theme.LightGreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ScrollTimePicker(
    selectedAmPm: MutableState<String>,
    selectedHour: MutableState<Int>,
    selectedMinute: MutableState<Int>
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().drawBehind {
            val targetHeight = size.height / 3
            drawRect(
                color = LightGreen,
                topLeft = Offset(0f, targetHeight),
                size = Size(size.width, targetHeight),
                alpha = 0.3f
            )
        }
    ) {
        val amPmList = listOf("", "오전", "오후", "")
        TimePickerColumn(
            items = amPmList,
            selectedItem = selectedAmPm,
        )

        Spacer(modifier = Modifier.width(32.dp))

        val hourList = listOf(-1) + (1..12).toList() + listOf(-1)
        TimePickerColumn(
            items = hourList,
            selectedItem = selectedHour,
            isHour = true,
            displayFunc = { if (it == -1) "" else it.toString() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        val minuteList = listOf(-1) + (0..59).toList() + listOf(-1)
        TimePickerColumn(
            items = minuteList,
            selectedItem = selectedMinute,
            isMinute = true,
            displayFunc = { if (it == -1) "" else it.toString().padStart(2, '0') }
        )
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun <T> TimePickerColumn(
    items: List<T>,
    selectedItem: MutableState<T>,
    isHour: Boolean = false,
    isMinute: Boolean = false,
    displayFunc: ((T) -> String)? = null
) {

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = items.indexOf(selectedItem.value) - 1
    )

    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.width(if (isHour || isMinute) 24.dp else 32.dp).height(78.dp)
    ) {
        itemsIndexed(items) { index, item ->
            if (index == 0 || index == items.lastIndex) {
                Spacer(modifier = Modifier.height(26.dp))
            } else {
                Text(
                    text = displayFunc?.invoke(item) ?: item.toString(),
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