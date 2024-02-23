package com.dicoding.githubuserapp.ui.detail.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.data.response.FollowResponseItem
import com.dicoding.githubuserapp.databinding.ItemFollowBinding

class FollowAdapter : ListAdapter<FollowResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemFollowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowResponseItem) {
            binding.tvItemName.text = item.login
            binding.tvItemType.text = item.type
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(binding.ivItemAvatar)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowResponseItem,
                newItem: FollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}