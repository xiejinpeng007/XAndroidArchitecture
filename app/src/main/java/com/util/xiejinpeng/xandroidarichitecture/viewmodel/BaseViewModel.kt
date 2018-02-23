package com.util.xiejinpeng.xandroidarichitecture.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    protected fun Disposable.addTo() = addTo(compositeDisposable)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}