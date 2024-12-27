package com.griya.griyabugar.data.model

data class PaketModel (
    val diskon : Int = 0,
    val harga : Int = 0,
    val kategori : String = "PROMOSI",
    val layanan : List<String> = emptyList(),
    val title : String = ""

)