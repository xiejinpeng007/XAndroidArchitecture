package com.util.xiejinpeng.xandroidarichitecture.manager.api.base

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import org.simpleframework.xml.core.Persister

/**
 * json & xml Serializer
 */


val globalMoshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

val simpleXmlPersister: Persister = Persister()