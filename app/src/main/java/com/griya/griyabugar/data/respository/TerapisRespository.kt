package com.griya.griyabugar.data.respository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.UploadPolicy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.data.model.UploadResponse
import com.griya.griyabugar.util.ImageProcess
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TerapisRespository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val mediaManager: MediaManager,
    @ApplicationContext private val context: Context
) {

    fun addTerapis(
        nama: String,
        jam_pulang: String,
        jam_masuk: String,
        foto_depan: Uri,
        foto_detail: Uri,
        layanan: List<String>,
        hari_kerja: List<String>,
    ): Flow<UploadResult<String>> = callbackFlow {

        trySend(UploadResult.Loading)

        try {
            val realPathFotoDepan = ImageProcess.getRealPathFromUri(context, foto_depan)
            val compressFotoDepan = ImageProcess.compressImage(realPathFotoDepan)

            val realPathFotoDetail = ImageProcess.getRealPathFromUri(context, foto_detail)
            val compressFotoDetail = ImageProcess.compressImage(realPathFotoDetail)

            val deffered1 = async {
                uploadImage(compressFotoDepan.absolutePath)
            }
            val defferd2 = async {
                uploadImage(compressFotoDetail.absolutePath)
            }

            val (result1, result2) = awaitAll(deffered1, defferd2)

            Log.d("addTerapis", "Result1: $result1")
            Log.d("addTerapis", "Result2: $result2")

            firebaseFirestore.collection(COLLECTION)
                .add(DataTerapis(
                    nama = nama,
                    hari_kerja = hari_kerja,
                    layanan = layanan,
                    jam_masuk = jam_masuk,
                    jam_pulang = jam_pulang,
                    foto_depan = result1.secure_url,
                    foto_depan_public_id = result1.public_id,
                    foto_detail = result2.secure_url,
                    foto_detail_public_id = result2.public_id,
                    timemilis = System.currentTimeMillis()
                ))
                .await()

            trySend(UploadResult.Success("Tambah terapis berhasil"))
            close()

        } catch (e: Exception){
            Log.e("addTerapis", "Error: ${e.message}")
            trySend(UploadResult.Error("Unexpected error occurred: ${e.message}"))
            close()
        }

        awaitClose {  }

    }.flowOn(Dispatchers.IO)

    private suspend fun uploadImage(filePath: String): UploadResponse = suspendCoroutine { continuation ->
        mediaManager.upload(filePath)
            .option("resource_type", "image")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {

                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    val gson = Gson()
                    val json = gson.toJson(resultData)
                    val uploadResponse = gson.fromJson(json, UploadResponse::class.java)
                    continuation.resume(uploadResponse)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    continuation.resumeWithException(Exception(error?.description ?: "Unknown error"))
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {

                }

            }).dispatch()
    }

    companion object {
        const val COLLECTION = "terapis"
        const val FOLDER = "terapis"
    }
}