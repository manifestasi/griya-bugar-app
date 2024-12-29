package com.griya.griyabugar.util

import java.time.LocalTime
import java.util.Calendar

fun isOpen(): Boolean {
    val bukaJam = 10
    val tutupJam = 22

    // Mendapatkan waktu sekarang
    val sekarang = Calendar.getInstance()
    val jamSekarang = sekarang.get(Calendar.HOUR_OF_DAY)

    // Mengecek apakah toko buka
    return jamSekarang in bukaJam until tutupJam
}