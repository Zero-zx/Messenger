package dev.proptit.messenger

import android.app.Application
import dev.proptit.messenger.data.MessengerDatabase
import dev.proptit.messenger.data.repository.MessageRepository
import dev.proptit.messenger.data.repository.UserRepository

class MainApplication : Application() {
    val messageRepository: MessageRepository by lazy {
        MessageRepository(
            MessengerDatabase.getInstance(
                this@MainApplication
            ).messageDao()
        )
    }
    val userRepository: UserRepository by lazy {
        UserRepository(
            MessengerDatabase.getInstance(
                this@MainApplication
            ).userDao()
        )
    }

    override fun onCreate() {
        super.onCreate()

    }
}