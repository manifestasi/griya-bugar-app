package com.griya.griyabugar.data.model

data class ItemPemesananModel(
    val uuid_doc : String = "",
    val id_paket :String = "",
    val id_terapis : String = "",
    val nama_terapis: String = "",
    val id_user : String = "",
    val rated : Boolean = false,
    val jam_pemesanan : String = "",
    val nomor_pesanan : Int = 0,
    val rating : Int = 0,
    val status : String = "",
    val tanggal_pemesanan : String = "",
    val tanggal_servis : String = ""

)