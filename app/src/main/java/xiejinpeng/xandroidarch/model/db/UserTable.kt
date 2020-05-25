package xiejinpeng.xandroidarch.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTable(@PrimaryKey(autoGenerate = true) var id: Long)