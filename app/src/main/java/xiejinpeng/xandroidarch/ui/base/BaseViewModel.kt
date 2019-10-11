package xiejinpeng.xandroidarch.ui.base

import android.app.Application
import androidx.lifecycle.*
import xiejinpeng.xandroidarch.util.BindLife
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app),
    LifecycleObserver,
    BindLife {

    override val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}