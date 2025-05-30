package com.example.myschedule.ui.createschedule

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SelectedDatesList(selectedDates: List<LocalDate>) {

    LazyColumn {
        items(selectedDates.sorted().groupConsecutiveDates()) { dateRange ->
            when (dateRange) {
                is DateGroup.SingleDate -> Text(
                    text = formatDate(dateRange.date),
                    color = Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                is DateGroup.ConsecutiveDate -> Text(
                    text = "${formatDate(dateRange.start)} ~ ${formatDate(dateRange.end)}",
                    color = Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private sealed class DateGroup {
    data class SingleDate(val date: LocalDate) : DateGroup()
    data class ConsecutiveDate(val start: LocalDate, val end: LocalDate) : DateGroup()
}

private fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("y년 M월 d일 EEEE"))
}

private fun List<LocalDate>.groupConsecutiveDates(): List<DateGroup> {
    if (isEmpty()) return emptyList()

    val listLength = this.size
    val result = mutableListOf<DateGroup>()
    var rangeStart = 0

    for (rangeEnd in 1 until listLength) {
        if (this[rangeEnd] != this[rangeEnd - 1].plusDays(1)) {
            if (rangeStart == rangeEnd - 1) {
                result.add(DateGroup.SingleDate(this[rangeStart]))
            } else {
                result.add(DateGroup.ConsecutiveDate(this[rangeStart], this[rangeEnd - 1]))
            }
            rangeStart = rangeEnd
        }
    }

    if (rangeStart == listLength - 1) {
        result.add(DateGroup.SingleDate(this[rangeStart]))
    } else {
        result.add(DateGroup.ConsecutiveDate(this[rangeStart], this[listLength - 1]))
    }

    return result
}
