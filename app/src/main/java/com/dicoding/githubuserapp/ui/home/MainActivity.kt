package com.dicoding.githubuserapp.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityMainBinding
import com.google.android.material.search.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    companion object {
        private var USER_LOGIN = "user_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitleTextAppearance(this@MainActivity, R.style.TextContent_TitleLarge)

        // When you display the searchView then press the back button, the searchView will disappear/hide.
        onBackPressedCallback = this@MainActivity.onBackPressedDispatcher.addCallback(this@MainActivity, false) {
            binding.searchView.hide()
        }

        // TODO
        /*
                binding.toolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu1 -> {

                            true
                        }
                        R.id.menu2 -> {

                            true
                        }
                        else -> false
                    }
                }
        */

        val layoutManager = LinearLayoutManager(this)
        binding.itemListUser.rvUser.layoutManager = layoutManager

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


                editText.setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    USER_LOGIN = searchView.text.toString()
                    searchView.hide()
                    mainViewModel.findUser(USER_LOGIN)
                    false
                }
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


}