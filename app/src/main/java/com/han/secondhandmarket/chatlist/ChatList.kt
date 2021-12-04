package com.han.secondhandmarket.chatlist

data class ChatList(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
){
    constructor(): this("","","", 0)
}
