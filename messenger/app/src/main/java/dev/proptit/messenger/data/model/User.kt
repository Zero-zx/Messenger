package dev.proptit.messenger.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tb")
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo("name") val name:String = "",
    @ColumnInfo("avatar")val avatarUri:String = "",
)