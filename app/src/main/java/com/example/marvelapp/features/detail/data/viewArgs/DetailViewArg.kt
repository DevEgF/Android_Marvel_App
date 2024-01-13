package com.example.marvelapp.features.detail.data.viewArgs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailViewArg(
    val name: String,
    val description: String,
    val imageUrl: String
): Parcelable
