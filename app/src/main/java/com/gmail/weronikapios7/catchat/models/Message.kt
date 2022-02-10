package com.gmail.weronikapios7.catchat.models



class Message(
    val message: String,
    val senderID: String,
    receiverId: String,
    timestamp: String
) {
    //, val senderImage: String
    constructor() : this("", "", "", "")
}