package com.example.marvelapp.commons.data

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)

fun ThumbnailResponse.getHttpsUrl() = "$path.$extension".replace("http", "https")
