package dev.proptit.messenger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.proptit.messenger.MainApplication
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.User
import dev.proptit.messenger.data.repository.MessageRepository
import dev.proptit.messenger.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _senderId = MutableLiveData(0)
    val senderId: LiveData<Int> = _senderId

    private var _receiverId = MutableLiveData(0)
    val receiverId: LiveData<Int> = _receiverId

     val allMessages: LiveData<List<Message>>

    init {
        allMessages = messageRepository.getAllMessages()
    }

    fun setSenderId(id: Int) {
        _senderId.value = id
    }

    fun setReceiverId(id: Int) {
        _receiverId.value = id
    }

    fun getAllChat(senderId: Int) {
        viewModelScope.launch {
            val listMessage = messageRepository.getBySenderId(senderId)
            val listChat = listMessage.groupBy({ it.receiverId }, { it.message })
        }
    }

    fun getChatById(idSend: Int = 0, idReceive: Int): List<Message> {
        return allMessages.value!!.filter {
            (it.senderId == idSend && it.receiverId == idReceive) || (it.senderId == idReceive && it.receiverId == idSend)
        }
    }

    fun insertMessage(message: Message) = viewModelScope.launch {
        messageRepository.insertMessage(message)
    }

    fun getAllUser(): LiveData<List<User>> = userRepository.getAllUser()

    fun getUserById(id: Int): LiveData<User> = userRepository.getUserById(id)
    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])


                return MainViewModel(
                    (application as MainApplication).messageRepository,
                    (application as MainApplication).userRepository
                ) as T
            }
        }
    }

}