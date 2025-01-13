package com.griya.griyabugar.data.respository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataNotificationModel
import com.griya.griyabugar.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
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

    companion object {
        const val COLLECTION = "notification"
    }

}