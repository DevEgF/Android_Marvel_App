package com.example.marvelapp.commons.data.network.response

data class DataWrapperResponse<T>(
    val copyright: String,
    val data: DataContainerResponse<T>
)
