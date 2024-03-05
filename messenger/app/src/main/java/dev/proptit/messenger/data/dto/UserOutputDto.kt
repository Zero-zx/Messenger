package dev.proptit.messenger.data.dto

import com.google.gson.annotations.SerializedName
import dev.proptit.messenger.data.model.User

data class UserOutputDto (
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("avatar")
    var avatarUri: String = ""
)

fun UserOutputDto.toLocal() = User(
    id = id,
    name = name,
    avatarUri = avatarUri
)