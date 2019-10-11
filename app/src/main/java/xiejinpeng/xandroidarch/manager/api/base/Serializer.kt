package xiejinpeng.xandroidarch.manager.api.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import xiejinpeng.xandroidarch.manager.api.base.KotshiApplicationJsonAdapterFactory
import org.simpleframework.xml.core.Persister
import se.ansman.kotshi.KotshiJsonAdapterFactory

/**
 * json & xml Serializer
 */


val globalMoshi: Moshi = Moshi.Builder()
    .add(ApplicationJsonAdapterFactory)
    .add(KotlinJsonAdapterFactory())
    .build()

val simpleXmlPersister: Persister = Persister()


@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        val INSTANCE: ApplicationJsonAdapterFactory =
            KotshiApplicationJsonAdapterFactory
    }
}