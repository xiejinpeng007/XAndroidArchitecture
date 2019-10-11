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
import xiejinpeng.xandroidarch.manager.di.sharedPrefModule

/**
 * Created by xiejinpeng on 2018/2/8.
 */
open class MainApplication : Application() , KodeinAware {

    override val kodein by Kodein.lazy {
        import(androidXModule(this@MainApplication))
        import(apiModule)
        import(sharedPrefModule)
        /* bindings */
    }

    override fun onCreate() {
        super.onCreate()


        AndroidThreeTen.init(this)
        Kotpref.init(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}