package xiejinpeng.xandroidarch

import com.facebook.stetho.Stetho
/**
 * Created by xiejinpeng on 2018/2/8.
 */
class DebugMainApplication : MainApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}