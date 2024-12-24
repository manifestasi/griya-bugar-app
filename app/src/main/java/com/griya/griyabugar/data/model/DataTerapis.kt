package com.griya.griyabugar.data.model

data class DataTerapis(
    val hari_kerja: List<String> = emptyList(),
    val jam_masuk: String = "",
    val jam_pulang: String = "",
    val layanan: List<String> = emptyList(),
    val nama: String = "",
    val foto_depan: String = "",
    val foto_depan_public_id: String = "",
    val foto_detail: String = "",
    val foto_detail_public_id: String = "",
    val timemilis: Long = 0L
)