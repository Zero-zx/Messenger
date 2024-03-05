package dev.proptit.messenger.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.proptit.messenger.data.dto.UserRegisterInputDto
import dev.proptit.messenger.data.remote.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val userService: UserService) : ViewModel() {

    fun register(
        name: String,
        username: String,
        password: String,
        onSuccess: (Int) -> Unit,
        onError: () -> Unit
    ) {
        val user = UserRegisterInputDto(name, "", username, password)

        val call = userService.register(user)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val id = response.body()
                    if (id != null) {
                        onSuccess(id)
                    } else {
                        onError()
                    }
                } else {
                    onError()
                    Log.d("regist", "here")

                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                onError()
            }
        })
    }

    class Factory(private val userService: UserService) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(userService) as T
        }
    }
}
