package com.griya.griyabugar.data.respository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.UploadPolicy
import com.cloudinary.utils.ObjectUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataDetailTerapisAndLayanan
import com.griya.griyabugar.data.model.DataService
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.Arrays
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TerapisRespository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val mediaManager: MediaManager,
    @ApplicationContext private val context: Context
) {

    fun deleteTerapis(
        id: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)

            val getDataDetailTerapis = firebaseFirestore.collection(COLLECTION)
                .document(id)
                .get()
                .await()

            val result = getDataDetailTerapis.toObject(DataTerapis::class.java)?.apply {
                this.id = getDataDetailTerapis.id
            }

            Log.d("deleteTerapis", "Result: $result")

            result?.foto_depan_public_id?.let { publicId ->
                mediaManager.cloudinary
                    .uploader()
                    .destroy(publicId, ObjectUtils.asMap("resource_type", "image", "folder", "terapis"))
            } ?: Log.e("deleteTerapis", "Foto Depan Public ID is null")

            result?.foto_detail_public_id?.let { publicId ->
                mediaManager.cloudinary
                    .uploader()
                    .destroy(publicId, ObjectUtils.asMap("resource_type", "image", "folder", "terapis"))
            } ?: Log.e("deleteTerapis", "Foto Detail Public ID is null")

            if (result != null) {
                firebaseFirestore.collection(COLLECTION)
                    .document(id)
                    .delete()
                    .await()
                emit(Resource.Success("Delete terapis berhasil"))
            } else {
                emit(Resource.Error("Data terapis tidak ditemukan"))
            }
        } catch (e: Exception) {
            Log.e("deleteTerapis", "Error: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)


    fun updateTerapis(
        id: String,
        nama: String,
        jam_pulang: String,
        jam_masuk: String,
        foto_depan: Uri?,
        foto_detail: Uri?,
        layanan: List<String>,
        hari_kerja: List<String>
    ): Flow<UploadResult<String>> = flow {

        emit(UploadResult.Loading)

        try {
            val getDataDetailTerapis = firebaseFirestore.collection(COLLECTION)
                .document(id)
                .get()
                .await()

            val result = getDataDetailTerapis.toObject(DataTerapis::class.java)?.apply {
                this.id = getDataDetailTerapis.id
            }

            var resultFotoDepan: UploadResponse? = null
            var resultFotoDetail: UploadResponse? = null

            // Jika foto_depan tidak null, upload gambar
            if (foto_depan != null) {
                val realPathFotoDepan = ImageProcess.getRealPathFromUri(context, foto_depan)
                val compressFotoDepan = ImageProcess.compressImage(context, realPathFotoDepan)
                resultFotoDepan = uploadImage(compressFotoDepan.absolutePath)
            }

            // Jika foto_detail tidak null, upload gambar
            if (foto_detail != null) {
                val realPathFotoDetail = ImageProcess.getRealPathFromUri(context, foto_detail)
                val compressFotoDetail = ImageProcess.compressImage(context, realPathFotoDetail)
                resultFotoDetail = uploadImage(compressFotoDetail.absolutePath)
            }

            // Bangun data update
            val updateData = DataTerapis(
                nama = nama,
                hari_kerja = hari_kerja,
                jam_masuk = jam_masuk,
                jam_pulang = jam_pulang,
                timemilis = result?.timemilis ?: 0L,
                layanan = layanan,
                foto_detail = result?.foto_detail ?: "",
                foto_depan = result?.foto_depan ?: "",
                foto_detail_public_id = result?.foto_detail_public_id ?: "",
                foto_depan_public_id = result?.foto_depan_public_id ?: ""
            )

            // Tambahkan URL gambar jika ada
            resultFotoDepan?.let {
                updateData.foto_depan = it.secure_url
                updateData.foto_depan_public_id = it.public_id
            }

            resultFotoDetail?.let {
                updateData.foto_detail = it.secure_url
                updateData.foto_detail_public_id = it.public_id
            }

            // Update ke Firestore
            firebaseFirestore.collection(COLLECTION)
                .document(id)
                .set(updateData)
                .await()

            emit(UploadResult.Success("Update terapis berhasil"))

        } catch (e: Exception) {
            Log.e("updateTerapis", "Error: ${e.message}")
            emit(UploadResult.Error("Unexpected error occurred: ${e.message}"))
        }

    }.flowOn(Dispatchers.IO)


    fun getDetailTerapisAndLayanan(id: String): Flow<Resource<DataDetailTerapisAndLayanan?>> = flow {
        emit(Resource.Loading)

        try {
            val dataLayanan = firebaseFirestore.collection(LayananRepository.COLLECTION)
                .get()
                .await()

            val resultLayanan = dataLayanan.documents.mapNotNull { layanan ->
                val dataLayanan = layanan.data!!

                DataService(
                    id = layanan.id,
                    nama = dataLayanan["nama"] as? String ?: ""
                )
            }

            val getDataDetailTerapis = firebaseFirestore.collection(COLLECTION)
                .document(id)
                .get()
                .await()

            val result = getDataDetailTerapis.toObject(DataTerapis::class.java)?.apply {
                this.id = getDataDetailTerapis.id
            }
            emit(Resource.Success(DataDetailTerapisAndLayanan(
                dataTerapis = result,
                dataService = resultLayanan
            )))

        } catch (e: Exception){
            Log.e("addTerapis", "Error: ${e.message}")
            emit(Resource.Error("Unexpected error occurred: ${e.message}"))
        }
    }

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
            Log.d("addTerapis", "foto_depan_uri: ${foto_depan}")
            Log.d("addTerapis", "foto_detail_uri: ${foto_detail}")

            val realPathFotoDepan = ImageProcess.getRealPathFromUri(context, foto_depan)
            val compressFotoDepan = ImageProcess.compressImage(context, realPathFotoDepan)

            val realPathFotoDetail = ImageProcess.getRealPathFromUri(context, foto_detail)
            val compressFotoDetail = ImageProcess.compressImage(context, realPathFotoDetail)

            Log.d("addTerapis", "absolutepath1: ${compressFotoDepan.absolutePath}")
            Log.d("addTerapis", "absolutepath2: ${compressFotoDetail.absolutePath}")

            val result1 = uploadImage(compressFotoDepan.absolutePath)
            val result2 = uploadImage(compressFotoDetail.absolutePath)

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

    @Suppress("UNCHECKED_CAST")
    fun getAllTerapis(): Flow<Resource<List<DataTerapis>>> = callbackFlow {
        trySend(Resource.Loading)

        try {

            val dataLayanan = firebaseFirestore.collection(LayananRepository.COLLECTION)
                .get()
                .await()

            val resultLayanan = dataLayanan.documents.mapNotNull { layanan ->
                val dataLayanan = layanan.data!!

                DataService(
                    id = layanan.id,
                    nama = dataLayanan["nama"] as? String ?: ""
                )
            }

            Log.d("getAllTerapis", resultLayanan.toString())

            val listener = firebaseFirestore.collection(COLLECTION)
                .addSnapshotListener { value, error ->
                    if (error != null){
                        trySend(Resource.Error(error.message.toString()))
                        close()
                        return@addSnapshotListener
                    }

                    // Data terapis dari snapshot listener
                    val resultDataTerapis = value?.documents?.map { terapis ->
                        val dataTerapis = terapis.data!!

                        DataTerapis(
                            id = terapis.id,
                            hari_kerja = dataTerapis["hari_kerja"] as? List<String> ?: emptyList(),
                            layanan = dataTerapis["layanan"] as? List<String> ?: emptyList(),
                            foto_depan_public_id = dataTerapis["foto_depan_public_id"] as? String ?: "",
                            jam_pulang = dataTerapis["jam_pulang"] as? String ?: "",
                            foto_depan = dataTerapis["foto_depan"] as? String ?: "",
                            foto_detail = dataTerapis["foto_detail"] as? String ?: "",
                            jam_masuk = dataTerapis["jam_masuk"] as? String ?: "",
                            nama = dataTerapis["nama"] as? String ?: "",
                            timemilis = dataTerapis["timemilis"] as? Long ?: 0L,
                            foto_detail_public_id = dataTerapis["foto_detail_public_id"] as? String ?: "",
                        )
                    } ?: emptyList()
                    Log.d("getAllTerapis", resultDataTerapis.toString())


                    // Map layanan ke setiap terapis berdasarkan filter
                    val filteredTerapis = resultDataTerapis.map { terapis ->
                        // Filter layanan berdasarkan kriteria tertentu, misalnya berdasarkan `id` atau kriteria lainnya
                        val relevantLayanan = resultLayanan.filter { layanan ->
                            // Contoh logika: Hanya layanan yang cocok dengan terapis (kriteria dapat disesuaikan)
                            layanan.id in terapis.layanan
                        }

                        // Set layanan yang relevan ke objek terapis
                        terapis.layanan = relevantLayanan.map { layanan ->
                            layanan.nama
                        }
                        terapis
                    }

                    Log.d("getAllTerapis", filteredTerapis.toString())
                    trySend(Resource.Success(filteredTerapis))
                }
            awaitClose { listener.remove() }

        } catch (e: Exception){
            Log.e("getAllTerapis", "Error: ${e.message}")
            trySend(Resource.Error("Unexpected error occurred: ${e.message}"))
            close()
            awaitClose {  }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun uploadImage(filePath: String): UploadResponse = suspendCoroutine { continuation ->
        mediaManager.upload(filePath)
            .option("resource_type", "image")
            .option("folder", "terapis")
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