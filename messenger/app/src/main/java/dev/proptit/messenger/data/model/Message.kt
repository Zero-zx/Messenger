package dev.proptit.messenger.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_tb")
data class Message(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo("sender_Id") val senderId:Int,
    @ColumnInfo("receiver_Id")val receiverId:Int,
    @ColumnInfo("message")val message:String = ""
//    val time: Long = 0L
)