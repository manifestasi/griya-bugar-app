package com.griya.griyabugar.di

import android.content.Context
import com.cloudinary.android.MediaManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideCloudinaryConfig(@ApplicationContext context: Context): MediaManager {
        val config: HashMap<String, String> = hashMapOf(
            "cloud_name" to "dgzoneznh", // Ganti dengan nama Cloudinary Anda
            "api_key" to "272585327642889", // Ganti dengan API Key Anda
            "api_secret" to "rltucdHuRl5DgzUOdqmRppCjiAc" // Ganti dengan API Secret Anda
        )

        MediaManager.init(context, config)
        return MediaManager.get()
    }
}