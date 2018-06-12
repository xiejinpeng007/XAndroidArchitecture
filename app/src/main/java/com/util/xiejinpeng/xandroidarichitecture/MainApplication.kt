package com.util.xiejinpeng.xandroidarichitecture

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.chibatching.kotpref.Kotpref
import com.jakewharton.threetenabp.AndroidThreeTen
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.util.xiejinpeng.xandroidarichitecture.model.db.AppDatabase
import com.util.xiejinpeng.xandroidarichitecture.model.db.AppDatabase.NAME
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

/**
 * Created by xiejinpeng on 2018/2/8.
 */
open class MainApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidModule(this@MainApplication))
    }

    override fun onCreate() {
        super.onCreate()

        FlowManager.init(
                FlowConfig.builder(this)
                        .addDatabaseConfig(
                                DatabaseConfig.builder(AppDatabase::class.java)
                                        .databaseName(NAME)
                                        .build())
                        .build())

        AndroidThreeTen.init(this)
        Kotpref.init(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}