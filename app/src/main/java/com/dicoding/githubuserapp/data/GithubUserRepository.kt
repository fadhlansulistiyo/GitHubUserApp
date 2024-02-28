package com.dicoding.githubuserapp.data

import androidx.lifecycle.MediatorLiveData
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity
import com.dicoding.githubuserapp.data.local.room.GithubUserDao
import com.dicoding.githubuserapp.data.remote.retrofit.ApiService
import com.dicoding.githubuserapp.util.AppExecutors

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<FavoriteUserEntity>>>()

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