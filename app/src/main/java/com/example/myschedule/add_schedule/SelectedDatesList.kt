package com.example.myschedule.add_schedule

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
    val groupedDates = selectedDates.sorted().groupConsecutiveDates()

    LazyColumn {
        items(groupedDates) { dateRange ->
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

private fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("y년 M월 d일 EEEE")
    return date.format(formatter)
}

private fun List<LocalDate>.groupConsecutiveDates(): List<DateGroup> {
    if (isEmpty()) return emptyList()

    val result = mutableListOf<DateGroup>()
    var rangeStart = 0

    for (rangeEnd in 1 until this.size) {
        if (this[rangeEnd] != this[rangeEnd - 1].plusDays(1)) {
            if (rangeStart == rangeEnd - 1) {
                result.add(DateGroup.SingleDate(this[rangeStart]))
            } else {
                result.add(DateGroup.ConsecutiveDate(this[rangeStart], this[rangeEnd - 1]))
            }
            rangeStart = rangeEnd
        }
    }

    if (rangeStart == this.size - 1) {
        result.add(DateGroup.SingleDate(this[rangeStart]))
    } else {
        result.add(DateGroup.ConsecutiveDate(this[rangeStart], this[this.size - 1]))
    }

    return result
}
