package com.util.xiejinpeng.xandroidarichitecture.ui.activity

import com.util.xiejinpeng.xandroidarichitecture.R
import com.util.xiejinpeng.xandroidarichitecture.ui.MainActivityTag
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity
import indi.yume.tools.fragmentmanager.BaseManagerFragment

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseActivity : BaseFragmentManagerActivity() {

    override fun fragmentViewId() = R.id.fragment_layout

    override fun baseFragmentWithTag(): MutableMap<String, Class<out BaseManagerFragment>> = MainActivityTag.toMap()


}