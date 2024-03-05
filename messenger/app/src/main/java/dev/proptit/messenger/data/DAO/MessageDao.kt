package dev.proptit.messenger.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.User

@Dao
interface MessageDao {
    @Query("SELECT * FROM message_tb")
    fun getAllMessages(): LiveData<List<Message>>
    @Insert(onConflict = IGNORE)
    suspend fun insertMessage(message: Message)


    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message_tb where sender_Id = :senderId")
    suspend fun getBySenderId(senderId: Int): List<Message>
}