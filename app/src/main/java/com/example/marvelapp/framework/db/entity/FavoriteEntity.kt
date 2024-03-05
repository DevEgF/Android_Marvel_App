package com.example.marvelapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelapp.commons.utils.Constants.DESCRIPTION_ROOM
import com.example.marvelapp.commons.utils.Constants.ID_ROOM
import com.example.marvelapp.commons.utils.Constants.IMAGE_URL_ROOM
import com.example.marvelapp.commons.utils.Constants.NAME_ROOM
import com.example.marvelapp.commons.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = ID_ROOM)
    val id: Int,
    @ColumnInfo(name = NAME_ROOM)
    val name: String,
    @ColumnInfo(name = IMAGE_URL_ROOM)
    val imageUrl: String,
    @ColumnInfo(name = DESCRIPTION_ROOM)
    val description: String
)
