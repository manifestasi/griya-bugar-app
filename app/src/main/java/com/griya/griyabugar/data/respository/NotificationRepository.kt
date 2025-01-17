package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataNotificationModel
import com.griya.griyabugar.util.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun sendNotification(
        idUser: String,
        title: String,
        text: String,
    ): Flow<Resource<String>> = flow {

        suspend fun send(){
            firebaseFirestore.collection(AuthRepository.COLLECTION_USER)
                .document(idUser)
                .collection(COLLECTION)
                .add(DataNotificationModel(
                    title = title,
                    text = text,
                    date = Date.getCurrentDateFromMillis2(),
                    timeStamp = System.currentTimeMillis()
                ))
                .await()

            Log.d("sendNotification", "Send notification success")
        }

        send()

    }

    fun deleteNotification(
        uidNotification: String
    ): Flow<Resource<String>> = flow {

        emit(Resource.Loading)

        try {
            val idUser = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")

            firebaseFirestore.collection(AuthRepository.COLLECTION_USER)
                .document(idUser)
                .collection(COLLECTION)
                .document(uidNotification)
                .delete()
                .await()

            emit(Resource.Success("Delete selected notification successfull"))

        } catch (e: Exception){
            Log.e("getAllNotification", "Error: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun getAllNotification(): Flow<Resource<List<DataNotificationModel>>> = flow {

        emit(Resource.Loading)

        try {
            val idUser = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Ambil semua notifikasi
            val result = firebaseFirestore.collection(AuthRepository.COLLECTION_USER)
                .document(idUser)
                .collection(COLLECTION)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val dataResult = result.documents.mapNotNull { doc ->
                doc.toObject(DataNotificationModel::class.java)?.copy(uid = doc.id)
            }

            // Update semua notifikasi yang belum dibaca menjadi sudah dibaca
            val unreadNotifications = firebaseFirestore.collection(AuthRepository.COLLECTION_USER)
                .document(idUser)
                .collection(COLLECTION)
                .whereEqualTo("read", false)
                .get()
                .await()

            if (!unreadNotifications.isEmpty) {
                val batch = firebaseFirestore.batch()
                for (document in unreadNotifications.documents) {
                    batch.update(document.reference, "read", true)
                }
                batch.commit().await() // Tunggu sampai batch selesai
            }

            emit(Resource.Success(dataResult))

        } catch (e: Exception) {
            Log.e("getAllNotification", "Error: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }

    }.flowOn(Dispatchers.IO)


    fun countNotificationUnread(): Flow<Resource<Int>> = callbackFlow {

        // Emit loading state
        trySend(Resource.Loading)

        val idUser = firebaseAuth.currentUser?.uid ?: ""
        if (idUser.isEmpty()) {
            trySend(Resource.Error("User not authenticated"))
            close(Exception("User not authenticated"))
            return@callbackFlow
        }

        // Firestore listener for realtime updates
        val listenerRegistration = firebaseFirestore.collection(AuthRepository.COLLECTION_USER)
            .document(idUser)
            .collection(COLLECTION)
            .whereEqualTo("read", false)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error("Error: ${error.message.toString()}"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    // Emit the count of unread notifications
                    val count = snapshot.size()
                    trySend(Resource.Success(count))
                } else {
                    trySend(Resource.Error("Snapshot is null"))
                }
            }

        // Close the flow when the coroutine is cancelled
        awaitClose {
            listenerRegistration.remove() // Remove Firestore listener
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        const val COLLECTION = "notification"
    }

}