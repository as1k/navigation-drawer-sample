package com.as1k.navigationdrawersample

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppMenuItem(
    val name: String?,
    val type: Int
) : Parcelable
