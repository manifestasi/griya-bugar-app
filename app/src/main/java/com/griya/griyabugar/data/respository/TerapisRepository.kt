package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.TerapisModel
import com.griya.griyabugar.data.respository.PaketRepository.Companion.PAKET_COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class TerapisRepository @Inject constructor(
    val firestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
) {

    fun getTerapisById(id_terapis:String): Flow<Resource<TerapisModel>> = callbackFlow {
        trySend(Resource.Loading)

        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Empty)
            close()
            return@callbackFlow
        }

        try {
            val result =  firestore.collection(TERAPIS_COLLECTION)
                .document(id_terapis)
                .get()
                .await()

            Log.d("PAKET", "PAKET : ${result.data}")

            val data_terapis = result?.toObject(TerapisModel::class.java)

            if(data_terapis != null){
                trySend(Resource.Success(data_terapis))
            }else{
                trySend(Resource.Empty)
            }
        }catch (e:Exception){
            trySend(Resource.Error("Data Terapis Gagal Dimuat!"))
        }

        awaitClose{

        }

    }.flowOn(Dispatchers.IO)

    companion object {
        const val TERAPIS_COLLECTION = "terapis"
    }
}