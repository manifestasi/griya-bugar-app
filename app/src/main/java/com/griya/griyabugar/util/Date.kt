package com.griya.griyabugar.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Date {
    fun getCurrentDateFromMillis(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date(currentTimeMillis))
    }

    fun getCurrentDateFromMillis2(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(currentTimeMillis))
    }

    fun getCurrentDateFromDay(day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getDayNameOld(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(dateString)
        val dayFormat = SimpleDateFormat("EEEE", Locale("id"))
        return dayFormat.format(date!!)
    }

    fun isTimeInRange(selected: String, start: String, end: String): Boolean {
        return try {
            val format = SimpleDateFormat("HH.mm", Locale.getDefault())

            val selectedDate = format.parse(selected)
            val startDate = format.parse(start)
            val endDate = format.parse(end)

            selectedDate != null && startDate != null && endDate != null &&
                    !selectedDate.before(startDate) && !selectedDate.after(endDate)
        } catch (e: Exception) {
            false
        }
    }
}