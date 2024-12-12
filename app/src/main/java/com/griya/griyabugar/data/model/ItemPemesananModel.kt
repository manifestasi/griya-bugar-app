package com.griya.griyabugar.data.model

data class ItemPemesananModel (
    val title : String,
    val tanggal : String,
    val jam : String,
    val item_servis : List<String>,
    val harga : String
)