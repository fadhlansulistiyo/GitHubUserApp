package com.dicoding.githubuserapp.di

import android.content.Context
import com.dicoding.githubuserapp.data.GithubUserRepository
import com.dicoding.githubuserapp.data.local.room.GithubUserDatabase
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import com.dicoding.githubuserapp.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubUserDatabase.getInstance(context)
        val dao = database.githubUserDao()
        val appExecutors = AppExecutors()
        return GithubUserRepository.getInstance(apiService, dao, appExecutors)
    }
}