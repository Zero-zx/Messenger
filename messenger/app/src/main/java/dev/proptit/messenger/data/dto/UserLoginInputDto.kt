package dev.proptit.messenger.data.dto

import com.google.gson.annotations.SerializedName

data class UserLoginInputDto(
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = ""
)