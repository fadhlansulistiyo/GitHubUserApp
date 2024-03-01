package com.dicoding.githubuserapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.data.local.entity.FavoriteUserEntity
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.githubuserapp.ui.home.ListUserAdapter
import com.dicoding.githubuserapp.util.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private var favoriteUser: List<FavoriteUserEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager


        favoriteViewModel.getListFavoriteUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, type = it.type, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            setListFavoriteUser(items)
        }
    }

    private fun setListFavoriteUser(user: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showDataIsFound(isFound: Boolean) {
        if (isFound) {
            binding.notFound.visibility = View.INVISIBLE
            binding.tvDataNotFound.visibility = View.INVISIBLE
            binding.ivNotFound.visibility = View.INVISIBLE
        } else {
            binding.notFound.visibility = View.VISIBLE
            binding.tvDataNotFound.visibility = View.VISIBLE
            binding.ivNotFound.visibility = View.VISIBLE
        }
    }
}