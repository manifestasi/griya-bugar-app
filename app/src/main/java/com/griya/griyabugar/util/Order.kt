package com.griya.griyabugar.util

object Order {
    fun generateOrderNumberWithTimestamp(): Int {
        val timestampPart = System.currentTimeMillis() % 1000000 // Ambil 6 digit terakhir dari waktu saat ini
        return timestampPart.toString().padStart(6, '0').toInt() // Pastikan selalu 6 digit
    }
}