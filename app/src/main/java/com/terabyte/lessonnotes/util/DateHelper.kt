package com.terabyte.lessonnotes.util

object DateHelper {

    fun monthIndexToMonthString(monthIndex: Int): String {
        val months = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        return months[monthIndex]
    }
}