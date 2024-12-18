package com.griya.griyabugar.util

import android.app.Activity
import android.content.Context

fun Context?.getActivity(): Activity? = this as? Activity

// Keluar dari aplikasi
fun finishAffinity(context: Context?) {
    val activity = context.getActivity()
    activity?.finishAffinity()
}
