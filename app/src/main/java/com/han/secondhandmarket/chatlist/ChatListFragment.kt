package com.han.secondhandmarket.chatlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.han.secondhandmarket.DBKey.Companion.CHILD_CHAT
import com.han.secondhandmarket.DBKey.Companion.DB_USERS
import com.han.secondhandmarket.R
import com.han.secondhandmarket.chatdetail.ChatRoomActivity
import com.han.secondhandmarket.databinding.FragmentChatlistBinding

class ChatListFragment:Fragment(R.layout.fragment_chatlist) {
    private var binding: FragmentChatlistBinding? = null
    private lateinit var chatListAdapter: ChatListAdapter
    private val chatRoomList = mutableListOf<ChatList>()
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentChatListBinding = FragmentChatlistBinding.bind(view)
        binding = fragmentChatListBinding
        chatListAdapter =  ChatListAdapter(onItemClicked = { chatRoom ->
            //채팅방으로 이동하는 코드
            context?.let {
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey",chatRoom.key)
                startActivity(intent)
            }
        })
        chatRoomList.clear()

        fragmentChatListBinding.chatListRecyclerView.adapter = chatListAdapter
        fragmentChatListBinding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        if(auth.currentUser == null){
            return
        }
        val chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid)
            .child(CHILD_CHAT)

        chatDB.addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatList::class.java)
                    model ?: return

                    chatRoomList.add(model)
                }
                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        chatListAdapter.notifyDataSetChanged()
    }
}