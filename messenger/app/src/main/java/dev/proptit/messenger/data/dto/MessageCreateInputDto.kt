package dev.proptit.messenger.data.dto

import com.google.gson.annotations.SerializedName

data class MessageCreateInputDto (
    @SerializedName("message")
    val message: String = "",
    @SerializedName("idSendContact")
    val senderId: Int = 0,
    @SerializedName("idReceiveContact")
    val receiverId: Int = 0,
    @SerializedName("time")
    val time: Long = 0
)