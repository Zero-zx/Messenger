package dev.proptit.messenger.data.remote

import dev.proptit.messenger.data.dto.UserLoginInputDto
import dev.proptit.messenger.data.dto.UserOutputDto
import dev.proptit.messenger.data.dto.UserRegisterInputDto
import dev.proptit.messenger.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("api/contact/login")
    fun login(@Body userLoginInputDto: UserLoginInputDto): Call<Int>
    @POST("api/contact/register")
    fun register(@Body userRegisterInputDto: UserRegisterInputDto): Call<Int>
    @GET("api/contact")
    fun getAllUser(): Call<List<User>>
    @GET("api/contact/get_contact_with_user/{id}")
    fun findAllUserById(@Path("id") id: Int): Call<List<UserOutputDto>>
}