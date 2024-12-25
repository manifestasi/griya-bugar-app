package com.griya.griyabugar.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object ImageProcess {

    fun getRealPathFromUri(context: Context, uri: Uri): String {
        var filePath: String = ""
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val fileName = it.getString(columnIndex)

            // Buat file sementara di cache
            val tempFile = File(context.cacheDir, fileName)
            context.contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            filePath = tempFile.absolutePath
        } ?: throw IllegalArgumentException("Invalid URI")
        return filePath
    }

    fun compressImage(context: Context, imageFilePath: String, quality: Int = 80): File {
        // Load gambar ke dalam Bitmap
        val bitmap = BitmapFactory.decodeFile(imageFilePath)

        // Buat file sementara untuk menyimpan gambar yang telah dikompres
        val compressedFileName = "compressed_image_${System.currentTimeMillis()}.jpg"
        val compressedFile = File(imageFilePath.substringBeforeLast("/") + "/$compressedFileName")
//        val compressedFile = File(context.cacheDir, "compressed/${UUID.randomUUID()}.jpg")

        // Kompres bitmap ke file sementara
        val outputStream = FileOutputStream(compressedFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        return compressedFile
    }

}