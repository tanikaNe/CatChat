package com.gmail.weronikapios7.catchat

import android.net.Uri

class UserItem(
    val uid: String,
    val username: String,
    val profileImage: String
){
    constructor() : this("", "", "")
}