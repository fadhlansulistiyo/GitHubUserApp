package com.dicoding.githubuserapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuserapp.data.GithubUserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repo: GithubUserRepository) : ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return repo.getThemeSetting()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repo.saveThemeSetting(isDarkModeActive)
        }
    }
}