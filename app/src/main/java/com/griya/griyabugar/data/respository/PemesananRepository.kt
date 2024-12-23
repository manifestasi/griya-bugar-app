package com.griya.griyabugar.data.respository

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.ItemPemesananModel
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

) {
    fun getPemesananData(): Flow<Resource<List<ItemPemesananModel>?>> = callbackFlow {
        val user_id = firebaseAuth.currentUser?.uid
        Log.d("USER ID : ", "USER ID : $user_id")

        if (user_id == null) {
            trySend(Resource.Error("User tidak ditemukan!"))
            close() // Menutup flow jika user_id tidak ditemukan
            return@callbackFlow
        }

        val listenerRegistration = firestore.collection(PEMESANAN_COLLECTION)
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
            // Bersihkan listener ketika Flow tidak lagi digunakan
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
        field : String,
        new_value:Any
    ):Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading)

        val id_user = firebaseAuth.currentUser?.uid

        if(id_user == null){
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


    companion object{
        const val PEMESANAN_COLLECTION = "pemesanan"
    }
}