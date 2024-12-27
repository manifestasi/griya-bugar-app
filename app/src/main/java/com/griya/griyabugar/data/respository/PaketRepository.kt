package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.PaketModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PaketRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore:FirebaseFirestore
) {

    /*
    *  Function untuk mendapatkan data paket by Id
    * */
    fun getPaketById(id_paket:String):Flow<Resource<PaketModel>> = flow {
        emit(Resource.Loading)

        try {
            val result = firestore.collection(PAKET_COLLECTION)
                .document(id_paket)
                .get()
                .await()

            Log.d("PAKET", "PAKET : ${result.data}")

            val data_paket = result?.toObject(PaketModel::class.java)

            if(data_paket != null){
                emit(Resource.Success(data_paket))
            }else{
                emit(Resource.Empty)
            }
        }catch (e:Exception){
            emit(Resource.Error("Data Paket Gagal Dimuat! | ${e.message.toString()}"))
        }

    }.flowOn(Dispatchers.IO)

    companion object{
        const val PAKET_COLLECTION = "paket"
    }

}