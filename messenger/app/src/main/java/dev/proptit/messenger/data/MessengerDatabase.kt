package dev.proptit.messenger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.proptit.messenger.data.DAO.MessageDao
import dev.proptit.messenger.data.DAO.UserDao
import dev.proptit.messenger.data.model.Message
import dev.proptit.messenger.data.model.User

@Database(entities = [User::class, Message::class], version = 1)
abstract class MessengerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object{
        @Volatile
        private var INSTANCE : MessengerDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : MessengerDatabase{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    MessengerDatabase::class.java,
                    "messenger_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}