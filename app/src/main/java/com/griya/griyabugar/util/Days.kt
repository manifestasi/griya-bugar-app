package com.griya.griyabugar.util

import java.util.Calendar

object Days {

    val getData = listOf(
        "Senin",
        "Selasa",
        "Rabu",
        "Kamis",
        "Jumat",
        "Sabtu",
        "Minggu"
    )

    fun getDaysWithDates(): List<Pair<String, Int>> {
        val daysOfWeek = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Ming")
        val calendar = Calendar.getInstance()

        // Format untuk mendapatkan tanggal hari ini
        val todayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 2 // Ubah indeks menjadi Senin-Minggu
        val startDate = calendar.get(Calendar.DATE)

        // Jika indeks negatif (karena hari Minggu pada Calendar default jadi 1), perbaiki indeks
        val normalizedTodayIndex = if (todayIndex < 0) 6 else todayIndex
        // Looping untuk mendapatkan hari dan tanggalnya
        return daysOfWeek.indices.map { index ->
            val dayIndex = (normalizedTodayIndex + index) % 7 // Mengatur urutan mulai dari hari ini
            val date = startDate + index
            val newCalendar = Calendar.getInstance()
            newCalendar.set(Calendar.DATE, date) // Atur tanggal baru
            Pair(daysOfWeek[dayIndex], newCalendar.get(Calendar.DATE))
        }
    }
}