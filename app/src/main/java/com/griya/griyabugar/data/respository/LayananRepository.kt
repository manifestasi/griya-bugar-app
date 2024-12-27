package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.data.model.LayananModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LayananRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    fun getAlllayanan(): Flow<Resource<List<DataService>>> = flow {
        emit(Resource.Loading)

        try {
            val getLayanan = firebaseFirestore.collection(COLLECTION)
                .get()
                .await()

            val result = getLayanan.documents.mapNotNull { document ->
                document.toObject(DataService::class.java)?.apply {
                    id = document.id
                }
            }

            emit(Resource.Success(result))

        } catch (e: Exception){
            Log.e("getAlllayanan", "Error: ${e.message}")
            emit(Resource.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)

    /*
    * FUNCTION UNTUK GET LAYANAN BY ID
    * */

    fun getLayananById(id:String): Flow<Resource<LayananModel>> = flow {
        emit(Resource.Loading)

        try{
            val result = firebaseFirestore.collection(LAYANAN_COLLECTION)
                .document(id)
                .get()
                .await()

            val data_layanan = result?.toObject(
                LayananModel::class.java
            )?.copy(
                uuid_doc = result.id
            )

            if(data_layanan != null){
                emit(Resource.Success(data_layanan))
            }else{
                emit(Resource.Empty)
            }

        }catch (e:Exception){
            emit(Resource.Error("Gagal memuat layanan!"))
        }
    }.flowOn(Dispatchers.IO)

    /*
    * FUNCTION UNTUK GET SEMUA LAYANAN
    * */

    fun getLayananData():Flow<Resource<List<LayananModel>>> = callbackFlow {
        trySend(Resource.Loading)
        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Error("User tidak ditemukan!"))
            close()
            return@callbackFlow
        }

        val listener_result = firebaseFirestore.collection(LAYANAN_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if(error != null){
                    Log.e("getLayananData", "Error: ${error.message}")
                    trySend(Resource.Error(error.message.toString()))
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val layananData = snapshot.documents.mapNotNull {
                            doc ->
                        doc.toObject(LayananModel::class.java)?.copy(
                            uuid_doc = doc.id
                        )
                    }

                    Log.d("getLayananData", "Data Layanan: $layananData")

                    if (layananData.isNotEmpty()) {
                        trySend(Resource.Success(layananData))
                    } else {
                        trySend(Resource.Empty)
                    }
                } else {
                    Log.d("getLayananData", "Dokumen Kosong")
                    trySend(Resource.Empty)
                }
            }
        awaitClose {
            listener_result.remove()
        }
    }.flowOn(Dispatchers.IO)

    /*
    *
    * INSERT DATA PEMESANAN
    * */

    fun insertDataPemesanan(
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

        val map_layanan = hashMapOf(
            field to new_value
        )

        val insertTask =  firebaseFirestore.collection(LAYANAN_COLLECTION)
            .add(map_layanan)

        val listeners = insertTask
            .addOnSuccessListener {
                trySend(Resource.Success(true))
                close()
            }
            .addOnFailureListener {
                trySend(Resource.Error("Error!, Gagal save layanan"))
                close()
            }

        awaitClose {
            listeners
        }

    }.flowOn(Dispatchers.IO)


    /*
    * UPDATE DATA LAYANAN
    * */
    fun updateDataLayanan(
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

        val updateTask =  firebaseFirestore.collection(LAYANAN_COLLECTION)
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
    * DELETE DATA LAYANAN
    * */
    fun deleteDataLayanan(
        uuid_doc: String
    ):Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading)

        val id_user = firebaseAuth.currentUser?.uid

        if(id_user == null){
            trySend(Resource.Error("User Invalid!"))
            close()
            return@callbackFlow
        }

        val deleteTask =  firebaseFirestore.collection(LAYANAN_COLLECTION)
            .document(uuid_doc)
            .delete()

        val listeners = deleteTask
            .addOnSuccessListener {
                trySend(Resource.Success(true))
                close()
            }
            .addOnFailureListener {
                trySend(Resource.Error("Error!, Gagal delete"))
                close()
            }

        awaitClose {
            listeners
        }
    }

    companion object {
        const val COLLECTION = "layanan"
        const val LAYANAN_COLLECTION = "layanan"
    }
}