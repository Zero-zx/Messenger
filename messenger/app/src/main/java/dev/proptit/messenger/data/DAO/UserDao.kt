package dev.proptit.messenger.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import dev.proptit.messenger.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_tb")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_tb WHERE id LIKE :id")
    fun getUserById(id: Int): LiveData<User>

    @Insert(onConflict = IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}