package com.griya.griyabugar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataService(
    var id: String = "",
    var nama: String = ""
) : Parcelable