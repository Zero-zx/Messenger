package dev.proptit.messenger.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.proptit.messenger.data.dto.UserLoginInputDto
import dev.proptit.messenger.data.remote.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val userService: UserService) : ViewModel() {

    fun login(
        username: String,
        password: String,
        onSuccess: (Int) -> Unit,
        onError: () -> Unit
    ) {
        val user = UserLoginInputDto(username, password)
        val call = userService.login(user)
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
            return LoginViewModel(userService) as T
        }
    }
}
