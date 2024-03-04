package com.dicoding.githubuserapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.githubuserapp.ui.favorite.FavoriteActivity
import com.dicoding.githubuserapp.ui.setting.SettingActivity
import com.dicoding.githubuserapp.util.ViewModelFactory
import com.google.android.material.search.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    companion object {
        private var USER_LOGIN = "user_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // When you display the searchView then press the back button, the searchView will disappear/hide.
        onBackPressedCallback =
            this@MainActivity.onBackPressedDispatcher.addCallback(this@MainActivity, false) {
                binding.searchView.hide()
            }

        binding.toolbar.setTitleTextAppearance(this@MainActivity, R.style.TextContent_TitleLarge)

        val layoutManager = LinearLayoutManager(this)
        binding.itemListUser.rvUser.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            setTheme(isDarkModeActive)
        }

        mainViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isFound.observe(this) {
            showDataIsFound(it)
        }

        with(binding) {
            searchView.apply {
                searchView.setupWithSearchBar(searchBar)
                addTransitionListener { _, _, newState ->
                    onBackPressedCallback.isEnabled =
                        newState == SearchView.TransitionState.SHOWN || newState == SearchView.TransitionState.SHOWING
                }

                editText.setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    USER_LOGIN = searchView.text.toString()
                    searchView.hide()
                    mainViewModel.findUser(USER_LOGIN)
                    false
                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setListUser(user: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(user)
        binding.itemListUser.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.itemListUser.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDataIsFound(isFound: Boolean) {
        if (isFound) {
            binding.itemListUser.notFound.visibility = View.VISIBLE
            binding.itemListUser.tvDataNotFound.visibility = View.VISIBLE
            binding.itemListUser.ivNotFound.visibility = View.VISIBLE
            binding.itemListUser.ivSearch.visibility = View.INVISIBLE
        } else {
            binding.itemListUser.notFound.visibility = View.INVISIBLE
            binding.itemListUser.tvDataNotFound.visibility = View.INVISIBLE
            binding.itemListUser.ivNotFound.visibility = View.INVISIBLE
            binding.itemListUser.ivSearch.visibility = View.INVISIBLE
        }
    }

    private fun setTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}