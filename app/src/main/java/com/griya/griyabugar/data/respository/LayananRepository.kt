package com.griya.griyabugar.data.respository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.LayananModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LayananRepository @Inject constructor(
    val firestore:FirebaseFirestore
) {

    fun getLayananById(id:String): Flow<Resource<LayananModel>> = flow {
        emit(Resource.Loading)

        try{
            val result = firestore.collection(LAYANAN_COLLECTION)
                .document(id)
                .get()
                .await()

            val data_layanan = result?.toObject(
                LayananModel::class.java
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

    companion object{
        const val LAYANAN_COLLECTION = "layanan"
    }
}