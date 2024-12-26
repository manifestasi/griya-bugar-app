package com.griya.griyabugar.data.respository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.UploadPolicy
import com.cloudinary.utils.ObjectUtils
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class PaketRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val mediaManager: MediaManager,
    @ApplicationContext private val context: Context
) {
    suspend fun getPaketList(): Resource<List<PaketModel>> {
        return try {
            // Mengambil data dari Firestore
            val paketSnapshot = firestore.collection("paket").get().await()
            val paketList = paketSnapshot.documents.mapNotNull { doc ->
                val layananIds = doc.get("layanan") as? List<String> ?: emptyList()
                PaketModel(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    diskon = doc.getDouble("diskon")?.toInt() ?: 0,
                    harga = doc.getDouble("harga")?.toInt() ?: 0,
                    kategori = doc.getString("kategori") ?: "",
                    layanan = layananIds,
                    fotoDepan = doc.getString("fotoDepan") ?: "",
                    fotoDetail = doc.getString("fotoDetail") ?: ""
                )
            }
            // Kembalikan hasil sebagai Success
            Resource.Success(paketList)
        } catch (e: Exception) {
            // Jika error, kembalikan error message
            Resource.Error("Gagal mengambil data: ${e.message}")
        }
    }


//    suspend fun getPaketWithLayananNames(): List<PaketModelWithLayanan> {
//        // Ambil semua dokumen dari koleksi `paket`
//        val paketSnapshot = firestore.collection("paket").get().await()
//        val paketList = paketSnapshot.documents.mapNotNull { doc ->
//            val layananIds = doc.get("layanan") as? List<String> ?: emptyList()
//            PaketModel(
//                title = doc.getString("title") ?: "",
//                diskon = doc.getDouble("diskon")?.toInt() ?: 0,
//                harga = doc.getDouble("harga")?.toInt() ?: 0,
//                kategori = doc.getString("kategori") ?: "",
//                layanan = layananIds,
//                fotoDepan = doc.getString("fotoDepan") ?: "",
//                fotoDetail = doc.getString("fotoDetail") ?: ""
//            )
//        }
//
//        // Gabungkan data layanan dengan nama
//        val layananIds = paketList.flatMap { it.layanan }.distinct()
//        val layananMap = if (layananIds.isNotEmpty()) {
//            firestore.collection("layanan")
//                .whereIn(FieldPath.documentId(), layananIds)
//                .get()
//                .await()
//                .documents.associate { it.id to (it.getString("nama") ?: "") }
//        } else {
//            emptyMap()
//        }
//
//        // Ganti UUID layanan dengan nama layanan
//        return paketList.map { paket ->
//            val layananNames = paket.layanan.mapNotNull { layananMap[it] }
//            PaketModelWithLayanan(
//                title = paket.title,
//                diskon = paket.diskon,
//                harga = paket.harga,
//                kategori = paket.kategori,
//                layananNames = layananNames,
//                fotoDepan = paket.fotoDepan,
//                fotoDetail =paket.fotoDetail
//            )
//        }
//    }

    suspend fun getPaketWithLayananNames(): List<PaketModelWithLayanan> {
        val paketSnapshot = firestore.collection("paket").get().await()
        val paketList = paketSnapshot.documents.mapNotNull { doc ->
            val layananIds = doc.get("layanan") as? List<String> ?: emptyList()
            PaketModel(
                id = doc.id, // Menggunakan UUID dari dokumen
                title = doc.getString("title") ?: "",
                diskon = doc.getDouble("diskon")?.toInt() ?: 0,
                harga = doc.getDouble("harga")?.toInt() ?: 0,
                kategori = doc.getString("kategori") ?: "",
                layanan = layananIds,
                fotoDepan = doc.getString("fotoDepan") ?: "",
                fotoDetail = doc.getString("fotoDetail") ?: ""
            )
        }

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

        return paketList.map { paket ->
            val layananNames = paket.layanan.mapNotNull { layananMap[it] }
            PaketModelWithLayanan(
                id = paket.id, // Menyertakan id dokumen
                title = paket.title,
                diskon = paket.diskon,
                harga = paket.harga,
                kategori = paket.kategori,
                layananNames = layananNames,
                fotoDepan = paket.fotoDepan,
                fotoDetail = paket.fotoDetail
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

    fun addPaketWithImages(
        paket: PaketModel,
        fotoDepanUri: Uri?,
        fotoDetailUri: Uri?
    ): Flow<UploadResult<Unit>> = callbackFlow {

        trySend(UploadResult.Loading)

        val uuidPaket = UUID.randomUUID().toString()

        val uploadImage = { uri: Uri?, folder: String, callback: (String?) -> Unit ->
            if (uri != null) {
                val realPath = ImageProcess.getRealPathFromUri(context, uri)
                val compressImage = ImageProcess.compressImage(realPath)

                mediaManager.upload(compressImage.absolutePath)
                    .option("public_id", "$folder/${uuidPaket}")
                    .option("overwrite", true)
                    .option("resource_type", "image")
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {
                            trySend(UploadResult.Loading)
                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                            val progress = ((bytes.toDouble() / totalBytes) * 100).toInt()
                            trySend(UploadResult.Progress(progress))
                        }

                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                            val url = resultData?.get("secure_url").toString()
                            callback(url)
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Error(error?.description ?: "Unknown upload error"))
                            callback(null)
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Reschedule)
                            callback(null)
                        }
                    }).dispatch()
            } else {
                callback(null)
            }
        }

        // Upload foto depan
        uploadImage(fotoDepanUri, "folder_paket_depan") { fotoDepanUrl ->
            uploadImage(fotoDetailUri, "folder_paket_detail") { fotoDetailUrl ->
                if (fotoDepanUrl == null || fotoDetailUrl == null) {
                    trySend(UploadResult.Error("Failed to upload images"))
                    close()
                } else {
                    // Tambahkan paket ke Firestore
                    val paketWithImages = paket.copy(
                        fotoDepan = fotoDepanUrl,
                        fotoDetail = fotoDetailUrl
                    )

                    firestore.collection("paket")
                        .add(paketWithImages)
                        .addOnSuccessListener {
                            trySend(UploadResult.Success(Unit))
                            close()
                        }
                        .addOnFailureListener { e ->
                            trySend(UploadResult.Error(e.message ?: "Failed to add package"))
                            close()
                        }
                }
            }
        }

        // Tunggu hingga callback selesai atau flow ditutup
        awaitClose { /* No operation */ }
    }

    fun updatePaketWithImages(
        paketId: String,
        updatedData: PaketModel,
        fotoDepanUri: Uri?,
        fotoDetailUri: Uri?
    ): Flow<UploadResult<Unit>> = callbackFlow {

        trySend(UploadResult.Loading)

        val uploadImage = { uri: Uri?, folder: String, callback: (String?) -> Unit ->
            if (uri != null) {
                val realPath = ImageProcess.getRealPathFromUri(context, uri)
                val compressImage = ImageProcess.compressImage(realPath)

                mediaManager.upload(compressImage.absolutePath)
                    .option("public_id", "$folder/$paketId")
                    .option("overwrite", true)
                    .option("resource_type", "image")
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {
                            trySend(UploadResult.Loading)
                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                            val progress = ((bytes.toDouble() / totalBytes) * 100).toInt()
                            trySend(UploadResult.Progress(progress))
                        }

                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                            val url = resultData?.get("secure_url").toString()
                            callback(url)
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Error(error?.description ?: "Unknown upload error"))
                            callback(null)
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Reschedule)
                            callback(null)
                        }
                    }).dispatch()
            } else {
                callback(null)
            }
        }

        // Upload foto depan (jika ada)
        uploadImage(fotoDepanUri, "folder_paket_depan") { fotoDepanUrl ->
            // Upload foto detail (jika ada)
            uploadImage(fotoDetailUri, "folder_paket_detail") { fotoDetailUrl ->

                // Update data Firestore
                val updatedPaket = updatedData.copy(
                    fotoDepan = fotoDepanUrl ?: updatedData.fotoDepan,
                    fotoDetail = fotoDetailUrl ?: updatedData.fotoDetail
                )

                firestore.collection("paket").document(paketId)
                    .set(updatedPaket)
                    .addOnSuccessListener {
                        trySend(UploadResult.Success(Unit))
                        close()
                    }
                    .addOnFailureListener { e ->
                        trySend(UploadResult.Error(e.message ?: "Failed to update package"))
                        close()
                    }
            }
        }

        // Tunggu hingga callback selesai atau flow ditutup
        awaitClose { /* No operation */ }
    }

    /*fun deletePaketWithImages(paketId: String, fotoDepanUrl: String?, fotoDetailUrl: String?): Flow<UploadResult<Unit>> = callbackFlow {
        trySend(UploadResult.Loading)

        val deleteImage = { imageUrl: String?, callback: (Boolean) -> Unit ->
            if (imageUrl != null) {
                val publicId = extractPublicIdFromUrl(imageUrl)
                if (publicId.isNotEmpty()) {
                    mediaManager.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())

                } else {
                    callback(true) // Tidak perlu menghapus gambar jika public_id kosong
                }
            } else {
                callback(true) // Tidak ada URL gambar, jadi dianggap berhasil
            }
        }

        // Hapus foto depan
        deleteImage(fotoDepanUrl) { fotoDepanDeleted ->
            if (!fotoDepanDeleted) {
                trySend(UploadResult.Error("Failed to delete front image"))
                close()
                return@deleteImage
            }

            // Hapus foto detail
            deleteImage(fotoDetailUrl) { fotoDetailDeleted ->
                if (!fotoDetailDeleted) {
                    trySend(UploadResult.Error("Failed to delete detail image"))
                    close()
                    return@deleteImage
                }

                // Hapus data paket dari Firestore
                firestore.collection("paket").document(paketId)
                    .delete()
                    .addOnSuccessListener {
                        trySend(UploadResult.Success(Unit))
                        close()
                    }
                    .addOnFailureListener { e ->
                        trySend(UploadResult.Error(e.message ?: "Failed to delete package"))
                        close()
                    }
            }
        }

        // Tunggu hingga callback selesai atau flow ditutup
        awaitClose { *//* No operation *//* }
    }*/
    fun deletePaketWithImages(paketId: String, fotoDepanUrl: String?, fotoDetailUrl: String?): Flow<UploadResult<Unit>> = callbackFlow {
        trySend(UploadResult.Loading)

        // Fungsi untuk menghapus gambar dengan menggunakan Cloudinary API
        suspend fun deleteImage(imageUrl: String?): Boolean {
            if (imageUrl != null) {
                val publicId = extractPublicIdFromUrl(imageUrl)
                if (publicId.isNotEmpty()) {
                    // Pastikan operasi penghapusan gambar dilakukan di latar belakang
                    return withContext(Dispatchers.IO) {
                        try {
                            mediaManager.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())
                            true
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
            }
            return true // Jika tidak ada gambar untuk dihapus
        }

        // Hapus foto depan dan foto detail di latar belakang menggunakan suspend function
        val fotoDepanDeleted = deleteImage(fotoDepanUrl)
        if (!fotoDepanDeleted) {
            trySend(UploadResult.Error("Failed to delete front image"))
            close()
            return@callbackFlow
        }

        val fotoDetailDeleted = deleteImage(fotoDetailUrl)
        if (!fotoDetailDeleted) {
            trySend(UploadResult.Error("Failed to delete detail image"))
            close()
            return@callbackFlow
        }

        // Hapus data paket dari Firestore
        firestore.collection("paket").document(paketId)
            .delete()
            .addOnSuccessListener {
                trySend(UploadResult.Success(Unit))
                close()
            }
            .addOnFailureListener { e ->
                trySend(UploadResult.Error(e.message ?: "Failed to delete package"))
                close()
            }

        // Tunggu hingga flow selesai atau ditutup
        awaitClose { /* No operation */ }
    }


    // Fungsi untuk mengekstrak public_id dari URL gambar Cloudinary
    private fun extractPublicIdFromUrl(url: String): String {
        val regex = Regex(".*cloudinary\\.com/(.*?)/v[0-9]+/.*$")
        return regex.find(url)?.groupValues?.get(1) ?: ""
    }






}