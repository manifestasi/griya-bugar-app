package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LayananRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
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

    companion object {
        const val COLLECTION = "layanan"
    }
}