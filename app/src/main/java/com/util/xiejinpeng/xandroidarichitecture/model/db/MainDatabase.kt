package com.util.xiejinpeng.xandroidarichitecture.model.db

import com.raizlabs.android.dbflow.annotation.Database
import com.util.xiejinpeng.xandroidarichitecture.model.db.AppDatabase.VERSION

/**
 * Created by xiejinpeng on 2018/2/8.
 */

@Database(version = VERSION)
object AppDatabase {
    const val VERSION = 1
    const val NAME = "x_app_database"
}
