package com.example.marvelapp.commons.data

data class DataWrapperResponse<T>(
    val copyright: String,
    val data: DataContainerResponse<T>
)
