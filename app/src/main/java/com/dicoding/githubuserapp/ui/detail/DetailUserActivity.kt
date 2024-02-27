package com.dicoding.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.remote.response.DetailUserResponse
import com.dicoding.githubuserapp.data.remote.retrofit.ApiConfig
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import com.dicoding.githubuserapp.ui.detail.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_LOGIN = "extra_login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // appBar
        setSupportActionBar(binding.detailToolbar)
        binding.detailToolbar.setTitleTextAppearance(this@DetailUserActivity, R.style.TextContent_TitleMedium)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // viewPager & tabLayout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.tabLayout.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabLayout.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
        // get username from Main Activity
        val username = intent.getStringExtra(EXTRA_LOGIN)
        // observe detailUser
        detailViewModel.detailUser.observe(this) { detailUser ->
            setDetailUserData(detailUser)
        }
        // observe progressBar
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (username != null) {
            detailViewModel.findDetailUser(username)
        }
    }

    private fun setDetailUserData(detailUser: DetailUserResponse) {
        binding.tvDetailName.text = detailUser.name
        binding.tvDetailUsername.text = detailUser.login
        binding.tvDetailFollowingNum.text = detailUser.following.toString()
        binding.tvDetailFollowersNum.text = detailUser.followers.toString()
        binding.tvDetailReposNum.text = detailUser.publicRepos.toString()
        binding.detailToolbar.title = detailUser.name

        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(binding.ivDetailAvatar)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}