package dev.proptit.messenger.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.proptit.messenger.data.dao.UserDao
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.data.remote.UserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource,
) {
    private val userService = ApiClient.userService
    val allUser = MutableLiveData<List<User>>()

    init {
        getUserFromRemote()
    }

    fun getAllLocalUser(): LiveData<List<User>> = userDao.getAllUser()
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
    fun getUserFromRemote() {

        val call = userService.getAllUser()

        call.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("debug", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                val data = response.body()
                if (data != null) {
                    allUser.value = data?: return
                    CoroutineScope(Dispatchers.IO).launch {
                        updateLocalDatabase(allUser)
                    }
                }
            }
        })
    }

    suspend fun updateLocalDatabase(users: MutableLiveData<List<User>>) =
        withContext(Dispatchers.IO) {
            if (users.value != null) {
                userDao.insertAll(users.value!!)
            }
        }

    fun getUserById(id: Int): LiveData<User> = userDao.getUserById(id)

}