package com.dicoding.githubuserapp.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.GithubUserRepository
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.data.remote.response.UserResponse
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import com.dicoding.githubuserapp.ui.detail.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFound = MutableLiveData<Boolean>()
    val isFound: LiveData<Boolean> = _isFound

    // insert data


    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUser(login: String) {
        _isLoading.value = true
        _isFound.value = false
        val client = ApiConfig.getApiService().getListUser(login)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.e(TAG, "onResponse")
                    _listUser.value = response.body()?.items
                    _isFound.value = response.body()?.totalCount == 0

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _isFound.value = true
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isFound.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}