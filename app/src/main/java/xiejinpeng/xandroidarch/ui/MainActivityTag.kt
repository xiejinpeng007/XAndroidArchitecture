package xiejinpeng.xandroidarch.ui

import xiejinpeng.xandroidarch.ui.base.BindingFragment
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity
import indi.yume.tools.fragmentmanager.BaseManagerFragment

/**
 * Created by xiejinpeng on 2018/2/12.
 */
//enum class MainActivityTag(val tag: String,
//                           val baseFragmentClazz: Class<out BaseManagerFragment>) {
//
//    Home("home_fragment", BindingFragment::class.java);
//
//
//    companion object {
//        fun toMap() = MainActivityTag.values()
//                .map { Pair(it.tag, it.baseFragmentClazz) }
//                .toMap(mutableMapOf())
//    }
//
//    fun swtichTag(activity: BaseFragmentManagerActivity,
//                  clearCurrentStack: Boolean = false) = activity.switchToStackByTag(tag, clearCurrentStack)
//
//}