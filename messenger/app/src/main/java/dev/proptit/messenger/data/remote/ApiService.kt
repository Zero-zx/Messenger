package dev.proptit.messenger.data.remote

import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>
}