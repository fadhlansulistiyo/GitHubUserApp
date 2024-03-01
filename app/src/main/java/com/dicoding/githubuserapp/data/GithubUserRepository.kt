package com.dicoding.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity
import com.dicoding.githubuserapp.data.local.room.GithubUserDao
import com.dicoding.githubuserapp.data.remote.retrofit.ApiService
import com.dicoding.githubuserapp.util.AppExecutors

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteUserEntity>>>()

    fun setFavoriteUser(favoriteUser: FavoriteUserEntity) {
        appExecutors.diskIO.execute { githubUserDao.insertFavoriteUser(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity> {
        return githubUserDao.getFavoriteUserByUsername(username)
    }

    fun getListFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return githubUserDao.getListFavoriteUser()
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            githubUserDao.deleteFavoriteUser(favoriteUser)
        }
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: GithubUserDao,
            appExecutors: AppExecutors
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}