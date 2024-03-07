package com.dicoding.githubuserapp.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity
import com.dicoding.githubuserapp.data.remote.response.DetailUserResponse
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import com.dicoding.githubuserapp.ui.detail.follow.SectionsPagerAdapter
import com.dicoding.githubuserapp.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private var isFavorite = false
    private var favoriteUser: FavoriteUserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

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
        val avatarURl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val type = intent.getStringExtra(EXTRA_TYPE)
        val url = intent.getStringExtra(EXTRA_URL)

        val btnFavorite = binding.btnFavorite

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

        detailViewModel.getFavoriteUserByUsername(username.toString()).observe(this) {
            favoriteUser = it
            isFavorite = if (favoriteUser != null) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.baseline_favorite_24
                    )
                )
                true
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                false

            }
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                detailViewModel.deleteFavoriteUser(favoriteUser as FavoriteUserEntity)
            } else {
                detailViewModel.setFavoriteUser(
                    FavoriteUserEntity(
                        username.toString(),
                        avatarURl.toString(),
                        type.toString(),
                        url.toString()
                    )
                )

            }
        }

        binding.detailToolbar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.action_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        val content = "${username}, \n${url}"
                        putExtra(Intent.EXTRA_TEXT, content)
                        this.type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    true
                }
                else -> false
            }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }
}