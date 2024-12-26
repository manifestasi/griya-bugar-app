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
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataUser
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


class UserDataRepository @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val mediaManager: MediaManager,
    @ApplicationContext private val context: Context
) {

    fun uploadDataProfile(
        uri: Uri?,
        email: String,
        noTelepon: String,
        nama: String,
        kelamin: String
    ): Flow<UploadResult<String>> = callbackFlow {

        trySend(UploadResult.Loading)

        val uuidUser = firebaseAuth.currentUser?.uid
        if (uuidUser.isNullOrEmpty()) {
            trySend(UploadResult.Error("User not logged in"))
            close()
            return@callbackFlow
        }

        try {
            if (uri != null) {
                val realPath = ImageProcess.getRealPathFromUri(context, uri)
                val compressImage = ImageProcess.compressImage(realPath)

                val requestId = mediaManager.upload(compressImage.absolutePath)
                    .option("public_id", "folder_profile/${uuidUser}")
                    .option("overwrite", true)
                    .option("resource_type", "image")
                    .policy(UploadPolicy.defaultPolicy())
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String?) {
                            trySend(UploadResult.Loading)
                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                            val progress = (bytes.toDouble() / totalBytes) * 100
                            trySend(UploadResult.Progress(progress.toInt()))
                        }

                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                            val url = resultData?.get("secure_url").toString()

                            firestore.collection(AuthRepository.COLLECTION_USER)
                                .document(uuidUser)
                                .get()
                                .addOnSuccessListener { snapshot ->
                                    val data = snapshot.data
                                    firestore.collection(AuthRepository.COLLECTION_USER)
                                        .document(uuidUser)
                                        .set(
                                            DataUser(
                                                nama = nama,
                                                noTelepon = noTelepon,
                                                email = email,
                                                role = data?.get("role").toString(),
                                                kelamin = kelamin,
                                                foto = url
                                            )
                                        )
                                        .addOnSuccessListener {
                                            trySend(UploadResult.Success("Update profile successful"))
                                            close()
                                        }
                                        .addOnFailureListener {
                                            trySend(UploadResult.Error(it.message.toString()))
                                            close()
                                        }
                                }
                                .addOnFailureListener {
                                    trySend(UploadResult.Error(it.message.toString()))
                                    close()
                                }
                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Error(error?.description ?: "Unknown error"))
                            close()
                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                            trySend(UploadResult.Error("Upload rescheduled"))
                            close()
                        }
                    }).dispatch()

                awaitClose { mediaManager.cancelRequest(requestId) }
            } else {
                val listener = firestore.collection(AuthRepository.COLLECTION_USER)
                    .document(uuidUser)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val data = snapshot.data
                        val foto = data?.get("foto")?.toString() ?: ""
                        firestore.collection(AuthRepository.COLLECTION_USER)
                            .document(uuidUser)
                            .set(
                                DataUser(
                                    nama = nama,
                                    noTelepon = noTelepon,
                                    email = email,
                                    role = data?.get("role").toString(),
                                    kelamin = kelamin,
                                    foto = foto
                                )
                            )
                            .addOnSuccessListener {
                                trySend(UploadResult.Success("Update profile successful"))
                                close()
                            }
                            .addOnFailureListener {
                                trySend(UploadResult.Error(it.message.toString()))
                                close()
                            }
                    }
                    .addOnFailureListener {
                        trySend(UploadResult.Error(it.message.toString()))
                        close()
                    }

                awaitClose { /* No operation */ }
            }
        } catch (e: Exception) {
            Log.e("UploadDataProfile", "Error: ${e.message}", e)
            trySend(UploadResult.Error("Unexpected error occurred: ${e.message}"))
            close()
        }
    }


    @SuppressLint("LongLogTag")
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

    /*
    * get user by id
    * */

    fun getUserById(uuid:String):Flow<Resource<DataUser>> = callbackFlow<Resource<DataUser>> {
        trySend(Resource.Loading)
        val user_id = firebaseAuth.currentUser?.uid

        if(user_id == null){
            trySend(Resource.Error("Invalid User!"))
            close()
            return@callbackFlow
        }

        try {
            val result = firestore.collection(USER_COLLECTION)
                .whereEqualTo("role", "customer")
                .get()
                .await()

            val filteredDocument = result.documents.find { it.id == uuid }

            val data_result = filteredDocument?.toObject(DataUser::class.java)

            if(data_result != null){
                trySend(Resource.Success(data_result))
            }else{
                trySend(Resource.Empty)
            }

        }catch (e:Exception){
            trySend(Resource.Error("Gagal get user!"))
        }

        awaitClose {  }

    }.flowOn(Dispatchers.IO)

    companion object{
        const val USER_COLLECTION = "user"
    }

}