package com.griya.griyabugar.data.respository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataNotificationModel
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.data.model.DataUserWithTokenMessaging
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.data.model.ItemPemesananModel2
import com.griya.griyabugar.util.Date
import com.griya.griyabugar.util.Fcm
import com.griya.griyabugar.util.Order
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PemesananRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging,
    @ApplicationContext private val context: Context
) {

    fun addPemesanan(
        id_paket: String,
        id_terapis: String,
        jam_pemesanan: String,
        rated: Boolean,
        tanggal_servis: String,
        kategori: String,
        paket: String
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)

        try {

            val user = firebaseAuth.currentUser

            firestore.collection(PEMESANAN_COLLECTION)
                .add(ItemPemesananModel2(
                    id_user = user?.uid ?: "",
                    id_terapis = id_terapis,
                    tanggal_pemesanan = Date.getCurrentDateFromMillis(),
                    status = "MENUNGGU",
                    id_paket = id_paket,
                    jam_pemesanan = jam_pemesanan,
                    rated = rated,
                    tanggal_servis = tanggal_servis,
                    nomor_pesanan = Order.generateOrderNumberWithTimestamp(),
                    timemilis = System.currentTimeMillis()
                ))
                .await()

            firestore.collection(AuthRepository.COLLECTION_USER)
                .document(user?.uid ?: "")
                .collection(NotificationRepository.COLLECTION)
                .add(
                    DataNotificationModel(
                    title = "Pesanan berhasil dibuat",
                    text = "$kategori, $paket pukul $jam_pemesanan WIB",
                    date = Date.getCurrentDateFromMillis2(),
                    timeStamp = System.currentTimeMillis()
                )
                )
                .await()

            val token = Fcm.getAccessToken(context)
            Fcm.sendFcmMessageTopic(
                accessToken = token,
                topic = AuthRepository.ADMIN,
                title = "Ada yang memesan",
                body = "Ada yang memesan paket $kategori, pukul $jam_pemesanan"
            )

            emit(Resource.Success("Berhasil di pesan"))

        } catch (e: Exception){
            Log.e("addPemesanan", "Error: ${e.message.toString()}")
            emit(Resource.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    fun getPemesananData(): Flow<Resource<List<ItemPemesananModel>?>> = callbackFlow {
        val user_id = firebaseAuth.currentUser?.uid
        Log.d("USER ID : ", "USER ID : $user_id")

        if (user_id == null) {
            trySend(Resource.Error("User tidak ditemukan!"))
            close()
            return@callbackFlow
        }

        val listenerRegistration = firestore.collection(PEMESANAN_COLLECTION)
            .orderBy("timemilis", Query.Direction.DESCENDING)
            .whereEqualTo("id_user", user_id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("getPemesananData", "Error: ${error.message}")
                    trySend(Resource.Error(error.message.toString()))
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val pemesananData = snapshot.documents.mapNotNull {
                        doc ->
                        doc.toObject(ItemPemesananModel::class.java)?.copy(
                            uuid_doc = doc.id
                        )
                    }

                    Log.d("getPemesananData", "Data Pemesanan: $pemesananData")

                    if (pemesananData.isNotEmpty()) {
                        trySend(Resource.Success(pemesananData))
                    } else {
                        trySend(Resource.Empty)
                    }
                } else {
                    Log.d("getPemesananData", "Dokumen Kosong")
                    trySend(Resource.Empty)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun getPemesananById(
        uuid_doc:String,
        ): Flow<Resource<ItemPemesananModel>> = callbackFlow {

        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Error("User tidak ditemukan!"))
            close()
            return@callbackFlow
        }

        try {

            val result = firestore.collection(PEMESANAN_COLLECTION)
                .document(uuid_doc)
                .get()
                .await()

            Log.d("IS RATED REPO : ", "IS RATED REPO : ${result.data.toString()}")

            val data_detail = result?.toObject(ItemPemesananModel::class.java)?.copy(
                uuid_doc = uuid_doc
            )
            Log.d("IS RATED REPO AFTER MAP : ", "IS RATED REPO AFTER MAP : ${data_detail.toString()}")


            if (data_detail != null){
                trySend(Resource.Success(data_detail))
            }else{
                trySend(Resource.Empty)
            }

        }catch (e:Exception){
            trySend(Resource.Error("Error memuat data : ${e.message.toString()}"))
        }

        awaitClose{

        }

    }.flowOn(Dispatchers.IO)


    fun updateDataPemesanan(
        uuid_doc: String,
        no_pesanan: String,
        field : String,
        new_value:Any,
        id_user: String
    ):Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading)

        val user_id = firebaseAuth.currentUser?.uid
        val user = firebaseAuth.currentUser

        if(user_id == null){
            trySend(Resource.Error("User Invalid!"))
            close()
            return@callbackFlow
        }

        val getDataUsers = firestore.collection(AuthRepository.COLLECTION_USER)
            .document(id_user)
            .get()
            .await()

        val dataUser = getDataUsers?.toObject(DataUserWithTokenMessaging::class.java)

        val token = dataUser?.tokenMessaging ?: ""

        val accessToken = Fcm.getAccessToken(context)
        Fcm.sendFcmMessageDeviceToken(
            accessToken = accessToken,
            title = "Pesanan $new_value",
            body = "Pesanan yang kamu pesan sudah $new_value",
            deviceToken = token
        )

        firestore.collection(AuthRepository.COLLECTION_USER)
            .document(id_user)
            .collection(NotificationRepository.COLLECTION)
            .add(
                DataNotificationModel(
                    title = "Pesanan $new_value",
                    text = "Pesanan dengan no $no_pesanan $new_value",
                    date = Date.getCurrentDateFromMillis2(),
                    timeStamp = System.currentTimeMillis()
                )
            )
            .await()

        val updateTask =  firestore.collection(PEMESANAN_COLLECTION)
            .document(uuid_doc)
            .update(
                field,
                new_value
            )
        val listeners = updateTask
            .addOnSuccessListener {
                trySend(Resource.Success(true))
                close()
            }
            .addOnFailureListener {
                trySend(Resource.Error("Error!, Gagal update"))
                close()
            }

        awaitClose {
            listeners
        }

    }.flowOn(Dispatchers.IO)


    fun updateDataPemesanan2(
        uuid_doc: String,
        field : String,
        new_value:Any,
    ):Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading)

        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Error("User Invalid!"))
            close()
            return@callbackFlow
        }

        val updateTask =  firestore.collection(PEMESANAN_COLLECTION)
            .document(uuid_doc)
            .update(
                field,
                new_value
            )
        val listeners = updateTask
            .addOnSuccessListener {
                trySend(Resource.Success(true))
                close()
            }
            .addOnFailureListener {
                trySend(Resource.Error("Error!, Gagal update"))
                close()
            }

        awaitClose {
            listeners
        }

    }.flowOn(Dispatchers.IO)


    /*
    * GET ALL PEMESANAN
    * */

    fun getPemesananAll():Flow<Resource<List<ItemPemesananModel>>> = callbackFlow {
        trySend(Resource.Loading)
        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Error("User tidak ditemukan!"))
            close()
            return@callbackFlow
        }

        val terapisSnapshot = firestore.collection(TerapisRepository.COLLECTION)
            .get()
            .await()

        val dataTerapis = terapisSnapshot.documents.mapNotNull { doc ->
            DataTerapis(
                id = doc.id,
                hari_kerja = doc.get("hari_kerja") as? List<String> ?: emptyList(),
                jam_masuk = doc.getString("jam_masuk") ?: "",
                jam_pulang = doc.getString("jam_pulang") ?: "",
                layanan = doc.get("layanan") as? List<String> ?: emptyList(),
                nama = doc.getString("nama") ?: "",
                foto_depan = doc.getString("foto_depan") ?: "",
                foto_depan_public_id = doc.getString("foto_depan_public_id") ?: "",
                foto_detail = doc.getString("foto_detail") ?: "",
                foto_detail_public_id = doc.getString("foto_detail_public_id") ?: "",
                timemilis = doc.getLong("timemilis") ?: 0L
            )
        }

        /* Buat map dari dataTerapis → id -> nama */
        val terapisMap = dataTerapis.associateBy({ it.id }, { it.nama })

        val listener_result = firestore.collection(PEMESANAN_COLLECTION)
            .orderBy("timemilis", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if(error != null){
                    Log.e("getLayananData", "Error: ${error.message}")
                    trySend(Resource.Error(error.message.toString()))
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {

                    /* Isi pemesananData sambil mengisi nama_terapis */
                    val pemesananData = snapshot.documents.mapNotNull { doc ->
                        val original = doc.toObject(ItemPemesananModel::class.java)
                        original?.copy(
                            uuid_doc = doc.id,
                            nama_terapis = terapisMap[original.id_terapis] ?: ""
                        )
                    }

                    Log.d("getPemesananData", "Data Pemesanan: $pemesananData")

                    if (pemesananData.isNotEmpty()) {
                        trySend(Resource.Success(pemesananData))
                    } else {
                        trySend(Resource.Empty)
                    }
                } else {
                    Log.d("getPemesananData", "Dokumen Kosong")
                    trySend(Resource.Empty)
                }
            }
        awaitClose {
            listener_result.remove()
        }
    }.flowOn(Dispatchers.IO)


    companion object{
        const val PEMESANAN_COLLECTION = "pemesanan"
    }
}