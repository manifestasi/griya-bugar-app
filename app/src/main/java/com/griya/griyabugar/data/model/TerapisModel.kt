package com.griya.griyabugar.data.model

data class TerapisModel (
    val hari_kerja:List<String> = emptyList(),
    val jam_masuk : String = "",
    val jam_pulang : String = "",
    val layanan : List<String> = emptyList(),
    val nama:String = "",
    val foto_depan:String? = ""
)