package com.example.week_11_video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.week_11_video.databinding.ItemBinding
import com.example.week_11_video.model.Item

class ItemsAdapter: ListAdapter<Item, ItemsAdapter.ItemViewHolder>(diff){
    companion object{
        val diff = object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ItemViewHolder(
            private val binding : ItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(item: Item){
            binding.title.text = item.name
            binding.price.text = item.price
            binding.description.text = item.description
            binding.image.setImageURI(item.image.toUri())

        }
    }
}