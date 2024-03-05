package dev.proptit.messenger.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.proptit.messenger.data.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message_tb")
    fun getAllMessages(): LiveData<List<Message>>
    @Insert(onConflict = IGNORE)
    suspend fun insertMessage(message: Message)
    @Insert(onConflict = REPLACE)
    suspend fun insertMessages(messages: List<Message>)
    @Delete
    suspend fun deleteMessage(message: Message)
    @Query("SELECT * FROM message_tb where sender_Id = :id or receiver_Id = :id")
    fun getAllMessagesById(id: Int): LiveData<List<Message>>

}