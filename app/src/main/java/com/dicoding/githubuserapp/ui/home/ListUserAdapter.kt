package com.dicoding.githubuserapp.ui.home


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ItemUserBinding
import com.dicoding.githubuserapp.ui.detail.DetailUserActivity
import com.dicoding.githubuserapp.ui.detail.follow.FollowFragment

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.tvItemName.text = item.login
            binding.tvItemType.text = item.type
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivItemAvatar)

            itemView.setOnClickListener {
                Intent(itemView.context, DetailUserActivity::class.java).apply {
                    putExtra(DetailUserActivity.EXTRA_LOGIN, item.login)
                    putExtra(FollowFragment.ARG_USERNAME, item.login)
                }.run {
                    itemView.context.startActivity(this)
                }
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}