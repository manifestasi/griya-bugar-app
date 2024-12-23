package com.griya.griyabugar.util

fun formatTimeInput(input: String): String {
    val sanitizedInput = input.filter { it.isDigit() }
    return when {
        sanitizedInput.length <= 2 -> sanitizedInput // Jam saja
        else -> {
            val hours = sanitizedInput.substring(0, 2)
            val minutes = sanitizedInput.substring(2).take(2) // Batas 2 digit untuk menit
            "$hours.$minutes"
        }
    }
}