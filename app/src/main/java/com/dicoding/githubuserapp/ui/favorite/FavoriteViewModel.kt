package com.dicoding.githubuserapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.GithubUserRepository
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity

class FavoriteViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    fun getListFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return githubUserRepository.getListFavoriteUser()
    }
}