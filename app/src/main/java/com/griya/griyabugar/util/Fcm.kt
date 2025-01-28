package com.griya.griyabugar.util

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object Fcm {
    fun getAccessToken(context: Context): String {
        val credentialsStream = context.assets.open("service-account.json") // File JSON ada di folder assets
        val credentials = GoogleCredentials
            .fromStream(credentialsStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))

        // Token akan diperbarui otomatis jika sudah kadaluarsa
        credentials.refreshIfExpired()

        return credentials.accessToken.tokenValue
    }

    fun sendFcmMessageTopic(
        accessToken: String,
        topic: String,
        title: String,
        body: String
    ) {
        val client = OkHttpClient()

        val json = """
        {
          "message": {
            "topic": "$topic",
            "notification": {
              "title": "$title",
              "body": "$body"
            }
          }
        }
    """.trimIndent()

        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://fcm.googleapis.com/v1/projects/griyabugar-d664a/messages:send")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                Log.d("sendFcmMessageTopic", "Notification sent successfully: ${response.body?.string()}")
            } else {
                Log.d("sendFcmMessageTopic", "Failed to send notification: ${response.code} - ${response.message}")
            }
        }
    }

    fun sendFcmMessageDeviceToken(
        accessToken: String,
        deviceToken: String,
        title: String,
        body: String
    ) {
        val client = OkHttpClient()

        val json = """
        {
          "message": {
            "token": "$deviceToken",
            "notification": {
              "title": "$title",
              "body": "$body"
            }
          }
        }
    """.trimIndent()

        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://fcm.googleapis.com/v1/projects/griyabugar-d664a/messages:send")
            .post(requestBody)
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                Log.d("sendFcmMessageDeviceToken", "Notification sent successfully: ${response.body?.string()}")
            } else {
                Log.d("sendFcmMessageDeviceToken", "Failed to send notification: ${response.code} - ${response.message}")
            }
        }
    }
}