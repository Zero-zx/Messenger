package dev.proptit.messenger.data.repository

import androidx.lifecycle.LiveData
import dev.proptit.messenger.data.DAO.MessageDao
import dev.proptit.messenger.data.DAO.UserDao
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.User

class MessageRepository(private val messageDao: MessageDao) {
    fun getAllMessages(): LiveData<List<Message>> = messageDao.getAllMessages()
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    suspend fun getBySenderId(senderId: Int): List<Message> = messageDao.getBySenderId(senderId)
}