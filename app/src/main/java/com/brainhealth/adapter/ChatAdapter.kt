package com.brainhealth.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainhealth.databinding.ItemChatBinding
import com.brainhealth.di.db.ChatData
import com.bumptech.glide.Glide

class ChatAdapter : ListAdapter<ChatData, ChatAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.MyViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ChatAdapter.MyViewHolder, position: Int) {
        val chat = getItem(position)
        if (chat != null) {
            holder.bind(chat)
        }
    }

    class MyViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: ChatData) {
            Glide.with(itemView.context).load(item.image).into(binding.imgProfile)
            binding.nameTitle.text = item.name
            binding.chat.text = item.chat
            binding.date.text = item.date

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatData>() {
            override fun areItemsTheSame(
                oldItem: ChatData,
                newItem: ChatData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ChatData,
                newItem: ChatData
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

}