package com.griya.griyabugar.util

import java.text.NumberFormat
import java.util.Locale

fun formatCurrencyIndonesia(amount: Int): String {
    val localeID = Locale("id", "ID") // Locale untuk Indonesia
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(amount).replace("Rp", "Rp ") // Menambahkan spasi setelah Rp
}