package com.example.marvelapp.features.heroes.domain.entities

import androidx.annotation.StringRes

data class DetailChildVO (
    val id: Int,
    val imageUrl: String
)

data class DetailParentVO (
    @StringRes
    val categoryStringResId: Int,
    val detailChildList: List<DetailChildVO> = listOf()
)