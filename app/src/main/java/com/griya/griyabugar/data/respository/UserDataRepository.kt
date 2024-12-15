package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserDataRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun getDataProfile(): Flow<Resource<DataUser?>> = flow {
        emit(Resource.Loading)

        try {

            val result = firestore
                .collection(AuthRepository.COLLECTION_USER)
                .document(firebaseAuth.currentUser?.uid ?: "")
                .get()
                .await()

            val getProfile = result?.toObject(DataUser::class.java)

            Log.d("getDataProfile", getProfile.toString())

            emit(Resource.Success(getProfile))

        } catch (e: Exception){
            Log.d("UserDataRepository.getDataProfile", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}