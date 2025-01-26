package com.griya.griyabugar.data.model

data class PaketModel(
    val id: String? = null, // UUID document
    val title: String? = null,
    val diskon: Int? = null,
    val harga: Int? = null,
    val kategori: String? = null,
    val layanan: List<String> = emptyList(),
    val fotoDepan: String? = null,
    val fotoDetail: String? = null
)

data class PaketModel2(
    val id: String? = null, // UUID document
    val title: String? = null,
    val diskon: Int? = null,
    val harga: Int? = null,
    val kategori: String? = null,
    val layanan: List<String> = emptyList(),
    val fotoDepan: String? = null,
    val fotoDetail: String? = null,
    val rating: Float = 0F,
)

data class PaketModelWithLayanan(
    val id: String? = null, // UUID document
    val title: String? = null,
    val diskon: Int? = null,
    val harga: Int? = null,
    val kategori: String? = null,
    val layananNames: List<String> = emptyList(),
    val fotoDepan: String? = null,
    val fotoDetail: String? = null
)

data class Layanan(
    val id: String = "", // UUID document
    val nama: String = "" // Nama layanan
)

data class PaketModelWithLayanan2(
    val id: String? = null, // UUID document
    val title: String? = null,
    val diskon: Int? = null,
    val harga: Int? = null,
    val kategori: String? = null,
    val layananNames: List<String> = emptyList(),
    val fotoDepan: String? = null,
    val fotoDetail: String? = null,
    val rating: Float = 0F,
)