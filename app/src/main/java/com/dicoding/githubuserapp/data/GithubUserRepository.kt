package com.dicoding.githubuserapp.data

import com.dicoding.githubuserapp.data.local.room.GithubUserDao
import com.dicoding.githubuserapp.data.remote.retrofit.ApiService

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val githubUserDao: GithubUserDao,
    private val appExecu
){
}