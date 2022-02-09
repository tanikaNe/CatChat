package com.gmail.weronikapios7.catchat.models

import android.net.Uri

class UserItem(
    val uid: String,
    val username: String,
    val profileImage: String
){
    constructor() : this("", "", "")
}