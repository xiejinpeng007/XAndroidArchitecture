package com.util.xiejinpeng.xandroidarichitecture

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by xiejinpeng on 2018/2/8.
 */
open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FlowManager.init(this)
    }
}