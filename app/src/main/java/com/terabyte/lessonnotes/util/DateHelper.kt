package com.terabyte.lessonnotes.util

import android.icu.util.Calendar
import java.util.Locale

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

        return String.format(Locale.US, "%02d.%02d.%d", day, month, year)
    }

    fun getDateFromMillis(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return String.format(Locale.US, "%02d.%02d.%d", day, month, year)
    }

    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getCurrentMonthIndex(): Int {
        return Calendar.getInstance().get(Calendar.MONTH)
    }
}