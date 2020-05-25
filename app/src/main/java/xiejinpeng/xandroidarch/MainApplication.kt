package xiejinpeng.xandroidarch

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.chibatching.kotpref.Kotpref
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import xiejinpeng.xandroidarch.manager.di.apiModule

/**
 * Created by xiejinpeng on 2018/2/8.
 */
open class MainApplication : Application() , KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@MainApplication))
        import(apiModule)
        /* bindings */
    }

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)

        if (BuildConfig.DEBUG)
        AndroidThreeTen.init(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}