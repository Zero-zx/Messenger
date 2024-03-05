package dev.proptit.messenger.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.proptit.messenger.data.dao.MessageDao
import dev.proptit.messenger.data.dto.MessageCreateInputDto
import dev.proptit.messenger.data.dto.MessageOutputDto
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.remote.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRepository(private val messageDao: MessageDao) {
    private val messageService = ApiClient.messageService
    private val allMessages = MutableLiveData<List<Message>>()
    fun getAllMessages(): LiveData<List<Message>> = messageDao.getAllMessages()
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    fun getAllMessageById(id: Int): LiveData<List<Message>> = messageDao.getAllMessagesById(id)


    init {
        getMessagesFromRemote()
    }

    private fun getMessagesFromRemote() {
        val call = messageService.getAllMessages()
        call.enqueue(object : Callback<List<MessageOutputDto>> {
            override fun onResponse(
                call: Call<List<MessageOutputDto>>,
                response: Response<List<MessageOutputDto>>
            ) {
                val data = response.body()
                if (data != null) {
                    allMessages.value = data.map {
                        Message(
                            it.id,
                            it.senderId,
                            it.receiverId,
                            it.message,
                            it.time
                        )
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    updateLocalDatabase(allMessages)
                }
            }

            override fun onFailure(call: Call<List<MessageOutputDto>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    suspend fun updateLocalDatabase(messages: MutableLiveData<List<Message>>) =
        withContext(Dispatchers.IO) {
            if (messages.value != null) {
                messageDao.insertMessages(messages.value!!)
            }
        }

    fun createMessage(message: MessageCreateInputDto){
        val call = messageService.createMessage(message)
        call.enqueue(object : Callback<MessageOutputDto>{
            override fun onResponse(
                call: Call<MessageOutputDto>,
                response: Response<MessageOutputDto>
            ) {
                Log.d("check", "push message to server succes")
            }

            override fun onFailure(call: Call<MessageOutputDto>, t: Throwable) {
                Log.d("check", t.message.toString())
            }

        })
    }
}