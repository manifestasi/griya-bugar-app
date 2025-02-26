package com.griya.griyabugar.data.model

data class DataNotificationModel(
    val uid: String = "",
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val read: Boolean = false,
    val timeStamp: Long = 0L
)