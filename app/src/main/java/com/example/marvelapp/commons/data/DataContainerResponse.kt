package com.example.marvelapp.commons.data

data class DataContainerResponse<T>(
    val offset: Int,
    val total: Int,
    val results: List<T>
)
