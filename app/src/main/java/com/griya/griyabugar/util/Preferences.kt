package com.griya.griyabugar.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object Preferences {
    fun saveToPreferences(context: Context, key: String, value: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getFromPreferences(context: Context, key: String): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    @SuppressLint("CommitPrefEdits")
    fun deleteFromPreferences(context: Context, key: String){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
    }
}