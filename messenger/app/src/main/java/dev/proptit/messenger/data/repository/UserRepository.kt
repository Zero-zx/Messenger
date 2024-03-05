package dev.proptit.messenger.data.repository

import androidx.lifecycle.LiveData
import dev.proptit.messenger.data.DAO.UserDao
import dev.proptit.messenger.data.model.User

class UserRepository(private val userDao: UserDao) {
    fun getAllUser(): LiveData<List<User>> = userDao.getAllUser()
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUserById(id: Int): LiveData<User> = userDao.getUserById(id)

}