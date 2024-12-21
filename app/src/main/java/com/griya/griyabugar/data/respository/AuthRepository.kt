package com.griya.griyabugar.data.respository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.util.Preferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
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

    fun updatePasswordWithOobCode(
        oobCode: String,
        newPassword: String,
        confirmNewPassword: String
    ): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading) // Mengirim status loading

        try {
            if (newPassword != confirmNewPassword) {
                trySend(Resource.Error("Kata sandi tidak sama"))
                close() // Menutup flow jika password tidak sama
                return@callbackFlow
            }

            firebaseAuth.confirmPasswordReset(oobCode, newPassword)
                .addOnSuccessListener {
                    trySend(Resource.Success("Ganti password berhasil"))
                    close() // Tutup flow setelah sukses
                }
                .addOnFailureListener { exception ->
                    trySend(Resource.Error("Ganti password gagal: ${exception.message}"))
                    close() // Tutup flow setelah gagal
                }

        } catch (e: Exception) {
            Log.e("AuthRepository", "Error: ${e.message}", e)
            trySend(Resource.Error("Unexpected error occurred: ${e.message}"))
            close()
        }

        awaitClose { /* Cleanup jika diperlukan */ }
    }.flowOn(Dispatchers.IO)


    @SuppressLint("LongLogTag")
    fun changePassword(
        password: String,
        oldPassword: String
    ): Flow<Resource<String>> = callbackFlow<Resource<String>> {
        trySend(Resource.Loading)

        try {

            val user = firebaseAuth.currentUser
            if (user == null) {
                trySend(Resource.Error("User tidak ditemukan atau belum login"))
                close()
                return@callbackFlow
            }

            if (password != oldPassword){
                trySend(Resource.Error("kata sandi tidak sama"))
                close()
                return@callbackFlow
            }

            user.updatePassword(password)
                .addOnSuccessListener {
                    trySend(Resource.Success("Ganti password berhasil"))
                    close()
                }
                .addOnFailureListener {
                    trySend(Resource.Error("Ganti password gagal"))
                    close()
                }

        } catch (e: Exception){
            Log.e("AuthRepository.changePassword", "Error: ${e.message}", e)
            trySend(Resource.Error("Unexpected error occurred: ${e.message}"))
            close()
        }

        awaitClose { /* Optional: Cleanup */ }
    }.flowOn(Dispatchers.IO)

    fun verifyOldPassword(password: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading) // Kirim status loading

        val user = firebaseAuth.currentUser
        if (user == null) {
            trySend(Resource.Error("User tidak ditemukan atau belum login"))
            close()
            return@callbackFlow
        }

        val email = user.email ?: ""
        val credential = EmailAuthProvider.getCredential(email, password)

        user.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Resource.Success("Verifikasi berhasil"))
                } else {
                    trySend(Resource.Error("Gagal verify password lama: ${task.exception?.message}"))
                }
                close() // Pastikan flow ditutup
            }
            .addOnFailureListener { exception ->
                trySend(Resource.Error("Error: ${exception.message}"))
                close() // Flow ditutup jika ada error
            }

        awaitClose { /* Optional: Cleanup jika perlu */ }
    }.flowOn(Dispatchers.IO)


    @SuppressLint("LongLogTag")
    fun sendForgotPasswordLink(email: String): Flow<Resource<String>> = callbackFlow {

        trySend(Resource.Loading)

        try {

            val url = "https://griyabugar.page.link/change_password"
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setAndroidPackageName("com.griya.griyabugar", true, null)
                .setHandleCodeInApp(true)
                .build()

            firebaseAuth.sendPasswordResetEmail(email, actionCodeSettings)
                .addOnSuccessListener {
                    trySend(Resource.Success("Cek email anda untuk mengubah kata sandi"))
                    close()
                }
                .addOnFailureListener {
                    trySend(Resource.Error(it.message.toString()))
                    close()
                }

        } catch (e: Exception){
            Log.e("AuthRepository.sendForgotPasswordLink", "Error: ${e.message}", e)
            trySend(Resource.Error("Unexpected error occurred: ${e.message}"))
            close()
        }

        awaitClose {}
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
    ): Flow<Resource<DataUser?>> = flow {

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

            if (dataUser == null){
                firebaseAuth.signOut()

                throw Exception("Oops, maaf kamu tidak di izinkan masuk")
            }

            Preferences.saveToPreferences(context = context, ROLE, dataUser.role ?: "")

            emit(Resource.Success(dataUser))
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
        const val CUSTOMER = "customer"
        const val ADMIN = "admin"
        const val ROLE = "role"
    }
}