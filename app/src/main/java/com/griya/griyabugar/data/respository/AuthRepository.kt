package com.griya.griyabugar.data.respository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.util.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.delay
import java.util.regex.Pattern
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return Pattern.compile(emailPattern).matcher(email).matches()
    }


    fun registerCustomer(
        nama: String,
        noTelepon: String,
        email: String,
        password: String,
        confirmPass: String
    ): Flow<Resource<String>> = flow {

        emit(Resource.Loading)
        Log.d("RegisterCustomer", "Loading started")

        try {

            if(nama.isEmpty()){
                throw Exception("Nama tidak boleh kosong")
            }

            if (noTelepon.isEmpty()){
                throw Exception("No telepon tidak boleh kosong")
            }

            if (email.isEmpty()){
                throw Exception("Email tidak boleh kosong")
            }

            if (!isValidEmail(email)){
                throw Exception("Email tidak valid")
            }

            if (password != confirmPass){
                throw Exception("Password dan confirm password tidak cocok")
            }

            // Menggunakan await untuk Firebase auth
            val authResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()

            val userData = DataUser(
                nama = nama,
                noTelepon = noTelepon,
                email = email,
                role = "customer"
            )

            firebaseFirestore
                .collection(COLLECTION_USER)
                .document(authResult.user?.uid ?: "")
                .set(userData)
                .await()

            emit(Resource.Success("Registrasi berhasil: ${authResult.user?.email}"))
            Log.d("RegisterCustomer", "Register success")

        } catch (e: Exception){
            Log.e("RegisterCustomer", "Error: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun loginAccount(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = flow {

        emit(Resource.Loading)

        try {

            if(email.isEmpty()){
                throw Exception("Nama tidak boleh kosong")
            }

            if(password.isEmpty()){
                throw Exception("Password tidak boleh kosong")
            }

            if (!isValidEmail(email)){
                throw Exception("Email tidak valid")
            }

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            val getData = firebaseFirestore
                .collection(COLLECTION_USER)
                .document(result.user?.uid ?: "")
                .get()
                .await()

            val dataUser = getData?.toObject(DataUser::class.java)

            if ((dataUser?.role ?: "") != "customer"){
                firebaseAuth.signOut()

                throw Exception("Oops, maaf kamu tidak di izinkan masuk")
            }

            Preferences.saveToPreferences(
                context = context,
                key = EMAIL_USER,
                value = dataUser?.email ?: ""
            )

            emit(Resource.Success(result.user))
        } catch (e: Exception){
            Log.e("loginAccount", "Error: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun logoutAccount(): Flow<Resource<Boolean>> = flow {
        try {

            emit(Resource.Loading)

            delay(2000)

            firebaseAuth.signOut()

            Preferences.deleteFromPreferences(
                context = context,
                key = EMAIL_USER
            )

            emit(Resource.Success(firebaseAuth.currentUser == null))

        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    companion object {
        const val COLLECTION_USER = "user"
        const val EMAIL_USER = "email"
    }
}