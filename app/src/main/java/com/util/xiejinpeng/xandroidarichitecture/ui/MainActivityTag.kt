package com.util.xiejinpeng.xandroidarichitecture.ui

import com.util.xiejinpeng.xandroidarichitecture.ui.fragment.HomeFragment
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity
import indi.yume.tools.fragmentmanager.BaseManagerFragment

/**
 * Created by xiejinpeng on 2018/2/12.
 */
enum class MainActivityTag(val tag: String,
                           val baseFragmentClazz: Class<out BaseManagerFragment>) {

    Home("home_fragment", HomeFragment::class.java);


    companion object {
        fun toMap() = MainActivityTag.values()
                .map { Pair(it.tag, it.baseFragmentClazz) }
                .toMap(mutableMapOf())
    }

    public fun swtichTag(activity: BaseFragmentManagerActivity,
                         clearCurrentStack: Boolean = false,
                         forceSwitch: Boolean = false) = activity.switchToStackByTag(tag)

}