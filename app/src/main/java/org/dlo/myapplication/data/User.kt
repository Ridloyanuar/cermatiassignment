package org.dlo.myapplication.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("url")
    var url: String,

    @SerializedName("login")
    var name: String,

    @SerializedName("bio")
    var bio: String,

    @SerializedName("avatar_url")
    var thumbnail: String
)