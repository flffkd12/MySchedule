package com.example.myschedule.schedulecreate.dateselect

import java.time.LocalDate

sealed class DateGroup {
    data class SingleDate(val date: LocalDate) : DateGroup()
    data class ConsecutiveDate(val start: LocalDate, val end: LocalDate) : DateGroup()
}
