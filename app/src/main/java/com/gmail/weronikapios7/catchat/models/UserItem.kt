package com.gmail.weronikapios7.catchat.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserItem(
    val uid: String,
    val username: String,
    val profileImage: String
): Parcelable{
    constructor() : this("", "", "")
}