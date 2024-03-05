package dev.proptit.messenger.data.remote

import dev.proptit.messenger.data.dto.MessageOutputDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    private const val BASE_URL = "http://34.124.219.119:8080/"

    val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient{
    val userService: UserService by lazy {
        RetrofitClient.retrofit.create(UserService::class.java)
    }

    val messageService: MessageService by lazy {
        RetrofitClient.retrofit.create(MessageService::class.java)
    }
}