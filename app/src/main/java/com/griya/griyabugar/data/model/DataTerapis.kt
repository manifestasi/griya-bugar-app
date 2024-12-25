package com.griya.griyabugar.data.model

data class DataTerapis(
    var id: String? = null,
    val hari_kerja: List<String> = emptyList(),
    val jam_masuk: String = "",
    val jam_pulang: String = "",
    var layanan: List<String> = emptyList(),
    val nama: String = "",
    var foto_depan: String = "",
    var foto_depan_public_id: String = "",
    var foto_detail: String = "",
    var foto_detail_public_id: String = "",
    val timemilis: Long = 0L
)