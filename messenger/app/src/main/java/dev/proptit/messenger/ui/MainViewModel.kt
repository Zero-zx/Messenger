package dev.proptit.messenger.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.proptit.messenger.MainApplication
import dev.proptit.messenger.data.dto.MessageCreateInputDto
import dev.proptit.messenger.data.dto.UserOutputDto
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.data.remote.ApiClient
import dev.proptit.messenger.data.remote.UserService
import dev.proptit.messenger.data.repository.MessageRepository
import dev.proptit.messenger.data.repository.UserRepository
import dev.proptit.messenger.setup.Keys.MY_ID
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class MainViewModel(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) : ViewModel() {
    private var _senderId = MutableLiveData(0)
    val senderId: LiveData<Int> = _senderId

    private var _receiverId = MutableLiveData(0)
    val receiverId: LiveData<Int> = _receiverId

    val allMessages: LiveData<List<Message>> = messageRepository.getAllMessages()

    val allUser: LiveData<List<User>> = userRepository.getAllLocalUser()

//    init {
//        Log.d("check", allUser.value.toString())
//    }


    fun setSenderId(id: Int) {
        _senderId.value = id
    }

    fun setReceiverId(id: Int) {
        _receiverId.value = id
    }
    fun getMessageByContactId(receiverId: Int, senderId: Int): MutableLiveData<List<Message>>
    {
        val listMessage = MutableLiveData<List<Message>>()
        messageRepository.getAllMessageById(receiverId).observeForever {
            listMessage.value = it.filter {
                it.receiverId == receiverId && it.senderId == senderId || it.receiverId == senderId && it.senderId == receiverId

            }
        }
        return listMessage
    }
fun getAllMessageById(id: Int): LiveData<List<Message>> = messageRepository.getAllMessageById(id)
    fun getUserById(id: Int): LiveData<User> = userRepository.getUserById(id)
    fun sendMessage(message: Message) = viewModelScope.launch{
        messageRepository.createMessage(MessageCreateInputDto(
            message = message.message,
            receiverId = message.receiverId,
            senderId = message.senderId,
            time = message.time
        ))
        messageRepository.insertMessage(message)
    }

    fun fetchDataFromRemote() {
        userRepository.getUserFromRemote()
    }
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel(
                    (application as MainApplication).messageRepository,
                    application.userRepository,
                    ApiClient.userService
                ) as T
            }
        }
    }

}