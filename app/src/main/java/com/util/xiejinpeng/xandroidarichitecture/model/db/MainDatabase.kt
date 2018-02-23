package com.util.xiejinpeng.xandroidarichitecture.model.db

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by xiejinpeng on 2018/2/8.
 */

@Database(name = MainDatabase.NAME, version = MainDatabase.VERSION)
class MainDatabase {
    companion object {
        const val NAME = "main_database"
        const val VERSION = 1
    }
}