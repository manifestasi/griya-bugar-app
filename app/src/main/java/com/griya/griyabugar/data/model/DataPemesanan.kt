package com.griya.griyabugar.data.model

data class DataPemesanan(
    val id: String = "",
    val layanan: List<String> = emptyList(),
    val jam_pemesanan: String = "",
    val kategori: String = "",
    val harga: Long = 0L,
    val diskon: Int = 0,
    val nomor_pemesanan: String = "",
    val rated: Boolean = false,
    val rating: Int = 0,
    val status: String = "",
    val tanggal_pemesanan: String = "",
    val tanggal_servis: String = "",
    val timemilis: Long = 0L
)