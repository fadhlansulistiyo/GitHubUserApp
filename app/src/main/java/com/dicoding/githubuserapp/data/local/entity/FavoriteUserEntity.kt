package com.dicoding.githubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorite_user")
class FavoriteUserEntity(
    @field:ColumnInfo(name = "username")
    val username: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name = "type")
    val type: String
)