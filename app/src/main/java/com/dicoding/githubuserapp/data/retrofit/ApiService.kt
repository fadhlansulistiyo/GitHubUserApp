package com.dicoding.githubuserapp.data.retrofit

import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.data.response.FollowResponseItem
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>
}