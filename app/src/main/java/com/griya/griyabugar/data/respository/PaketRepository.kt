package com.griya.griyabugar.data.respository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.UploadPolicy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.model.Layanan
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.util.ImageProcess
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PaketRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val mediaManager: MediaManager,
    @ApplicationContext private val context: Context
) {
    fun getDataPaket(): Flow<Resource<List<PaketModel>>> = flow {
        emit(Resource.Loading)

        try {
            val result = firestore.collection("paket").get().await()
            val paketList = result.documents.mapNotNull { doc ->
                doc.toObject(PaketModel::class.java)
            }

            emit(Resource.Success(paketList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPaketWithLayananNames(): List<PaketModelWithLayanan> {
        // Ambil semua dokumen dari koleksi `paket`
        val paketSnapshot = firestore.collection("paket").get().await()
        val paketList = paketSnapshot.documents.mapNotNull { doc ->
            val layananIds = doc.get("layanan") as? List<String> ?: emptyList()
            PaketModel(
                title = doc.getString("title") ?: "",
                diskon = doc.getDouble("diskon")?.toInt() ?: 0,
                harga = doc.getDouble("harga")?.toInt() ?: 0,
                kategori = doc.getString("kategori") ?: "",
                layanan = layananIds,
                fotoDepan = doc.getString("fotoDepan") ?: "",
                fotoDetail = doc.getString("fotoDetail") ?: ""
            )
        }

        // Gabungkan data layanan dengan nama
        val layananIds = paketList.flatMap { it.layanan }.distinct()
        val layananMap = if (layananIds.isNotEmpty()) {
            firestore.collection("layanan")
                .whereIn(FieldPath.documentId(), layananIds)
                .get()
                .await()
                .documents.associate { it.id to (it.getString("nama") ?: "") }
        } else {
            emptyMap()
        }

        // Ganti UUID layanan dengan nama layanan
        return paketList.map { paket ->
            val layananNames = paket.layanan.mapNotNull { layananMap[it] }
            PaketModelWithLayanan(
                title = paket.title,
                diskon = paket.diskon,
                harga = paket.harga,
                kategori = paket.kategori,
                layananNames = layananNames,
                fotoDepan = paket.fotoDepan,
                fotoDetail =paket.fotoDetail
            )
        }
    }

    fun getPaketWithLayananName(): Flow<Resource<List<PaketModelWithLayanan>>> = flow {
        emit(Resource.Loading)

        try {
            val paketList = getPaketWithLayananNames()
            emit(Resource.Success(paketList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    // Fungsi untuk mengambil koleksi layanan
    suspend fun getLayanan(): Resource<List<Layanan>> {
        return try {
            val snapshot = firestore.collection("layanan").get().await()
            val layananList = snapshot.documents.map { doc ->
                Layanan(
                    id = doc.id,
                    nama = doc.getString("nama") ?: ""
                )
            }
            Resource.Success(layananList)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan saat memuat layanan.")
        }
    }

    // Fungsi untuk menambahkan paket
    suspend fun addPaket(paket: PaketModel): Resource<Unit> {
        return try {
            firestore.collection("paket").add(paket).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan saat menambahkan paket.")
        }
    }

}