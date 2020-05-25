package xiejinpeng.xandroidarch.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by xiejinpeng on 2018/2/12.
 */
@Database(entities = [UserTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME_APP = "database_name_app"
    }
}