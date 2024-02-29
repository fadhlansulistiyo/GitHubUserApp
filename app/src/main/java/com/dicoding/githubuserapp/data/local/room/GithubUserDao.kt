package com.dicoding.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

    @Delete
    fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity)
}