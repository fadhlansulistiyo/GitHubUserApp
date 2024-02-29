package com.dicoding.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUserEntity(

    @field:ColumnInfo(name = "username")
    @PrimaryKey(autoGenerate = false)
    var username: String,

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String,

    @field:ColumnInfo(name = "type")
    var type: String

) : Parcelable