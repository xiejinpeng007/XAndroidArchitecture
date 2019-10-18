package xiejinpeng.xandroidarch.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import xiejinpeng.xandroidarch.ui.base.BaseViewModel

/**
 *  Shared data between fragments.
 */

class SharedViewModel(application: Application) : BaseViewModel(application){

    val sharedData = MutableLiveData<Int>(0)
}