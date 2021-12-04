package com.han.secondhandmarket.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.han.secondhandmarket.databinding.ItemChatListBinding

class ChatListAdapter(val onItemClicked: (ChatList) -> Unit): ListAdapter<ChatList, ChatListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatListItem: ChatList){

            binding.root.setOnClickListener {
                onItemClicked(chatListItem)
            }
            binding.chatRoomListTextView.text = chatListItem.itemTitle
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ChatList>(){
            override fun areItemsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem == newItem
            }

        }
    }
}