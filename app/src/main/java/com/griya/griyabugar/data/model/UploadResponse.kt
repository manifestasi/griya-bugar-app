package com.griya.griyabugar.data.model

data class UploadResponse(
    val public_id: String,
    val asset_id: String,
    val version: Long,
    val signature: String,
    val width: Int,
    val height: Int,
    val format: String,
    val resource_type: String,
    val created_at: String,
    val bytes: Int,
    val type: String,
    val access_mode: String,
    val url: String,
    val secure_url: String,
    val etag: String
)