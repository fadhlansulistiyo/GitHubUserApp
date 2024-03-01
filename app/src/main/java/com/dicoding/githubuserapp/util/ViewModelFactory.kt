package com.dicoding.githubuserapp.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserapp.data.GithubUserRepository
import com.dicoding.githubuserapp.di.Injection
import com.dicoding.githubuserapp.ui.detail.DetailViewModel
import com.dicoding.githubuserapp.ui.favorite.FavoriteViewModel
import com.dicoding.githubuserapp.ui.home.MainViewModel
import com.dicoding.githubuserapp.ui.setting.SettingViewModel

class ViewModelFactory private constructor(private val githubUserRepository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(githubUserRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(githubUserRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(githubUserRepository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(githubUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}