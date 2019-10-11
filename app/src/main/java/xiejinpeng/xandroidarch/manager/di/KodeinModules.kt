package xiejinpeng.xandroidarch.manager.di

import com.chibatching.kotpref.Kotpref
import com.photo.utils.moshipref.moshi
import xiejinpeng.xandroidarch.manager.api.base.ApiClient
import xiejinpeng.xandroidarch.manager.api.base.HeaderInterceptor
import xiejinpeng.xandroidarch.manager.api.base.NetErrorInterceptor
import xiejinpeng.xandroidarch.model.sharedpref.SharedPrefModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeUnit

val apiModule = Kodein.Module("api") {
    bind<ApiClient>() with singleton { provideApiClient() }
}

val sharedPrefModule = Kodein.Module("sharedpref") {
    bind<SharedPrefModel>() with singleton {
        Kotpref.moshi = instance()
        SharedPrefModel
    }
}

fun provideApiClient(): ApiClient {
    val client = ApiClient.Builder()

    client.okBuilder
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(NetErrorInterceptor())
        .readTimeout(30, TimeUnit.SECONDS)

    return client.build()
}