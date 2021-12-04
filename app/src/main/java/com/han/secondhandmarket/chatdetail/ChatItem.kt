package com.han.secondhandmarket.chatdetail

data class ChatItem(
    val sendId: String,
    val message: String
){
    constructor(): this("", "")
}
