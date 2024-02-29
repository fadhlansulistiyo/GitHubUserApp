package com.dicoding.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.GithubUserRepository
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity
import com.dicoding.githubuserapp.data.remote.response.DetailUserResponse
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // insert data
    fun setFavoriteUser(favoriteUser: FavoriteUserEntity) {
        githubUserRepository.setFavoriteUser(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity> {
        return githubUserRepository.getFavoriteUserByUsername(username)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity) =
        githubUserRepository.deleteFavoriteUser(favoriteUser)

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}