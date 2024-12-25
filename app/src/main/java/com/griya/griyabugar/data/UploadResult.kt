package com.griya.griyabugar.data

sealed class UploadResult<out R> private constructor() {
    object Idle : UploadResult<Nothing>()
    object Loading: UploadResult<Nothing>()
    data class Success<out T>(val data: T) : UploadResult<T>()
    data class Progress(val progress: Int) : UploadResult<Nothing>()
    data class Error(val errorMessage: String) : UploadResult<Nothing>()
    object Reschedule : UploadResult<Nothing>()
}