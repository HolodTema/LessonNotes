package com.terabyte.lessonnotes.util

import android.icu.util.Calendar

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

    fun getCurrentDate(): String {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val year = Calendar.getInstance().get(Calendar.YEAR)
        return "$day.$month.$year"
    }
}