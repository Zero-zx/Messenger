package dev.proptit.messenger.data.remote

import dev.proptit.messenger.data.dto.MessageCreateInputDto
import dev.proptit.messenger.data.dto.MessageOutputDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageService {
    @GET("api/message/{id}")
    fun findAllMessageById(@Path("id") idContact: Int): Call<List<MessageOutputDto>>
    @POST("api/message/create")
    fun createMessage(@Body message : MessageCreateInputDto): Call<MessageOutputDto>
    @GET("api/message")
    fun getAllMessages(): Call<List<MessageOutputDto>>
}