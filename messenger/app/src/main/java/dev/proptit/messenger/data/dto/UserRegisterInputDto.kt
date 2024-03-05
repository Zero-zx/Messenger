package dev.proptit.messenger.data.dto

import com.google.gson.annotations.SerializedName

data class UserRegisterInputDto(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = ""
)