package dev.proptit.messenger.data.remote

import android.content.res.Resources
import android.util.Log
import dev.proptit.messenger.data.dto.UserOutputDto
import dev.proptit.messenger.data.dto.toLocal
import dev.proptit.messenger.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataSource() {
    private val userService = ApiClient.userService
    var allUser = listOf<User>()
//    suspend fun getUsers(): Unit = withContext(Dispatchers.IO){
//        val call = userService.getAllUser()
//        call.enqueue(object: Callback<List<UserOutputDto>>{
//            override fun onResponse(
//                call: Call<List<UserOutputDto>>,
//                response: Response<List<UserOutputDto>>
//            ) {
//                allUser = response.body()?.map { it.toLocal() } ?:throw Resources.NotFoundException()
//                Log.d("remote", "get users from remote success")
//            }
//
//            override fun onFailure(call: Call<List<UserOutputDto>>, t: Throwable) {
//                Log.d("remote", "get users from remote fail")
//
//            }
//
//        })
//    }
}