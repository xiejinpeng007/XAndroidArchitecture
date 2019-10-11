package xiejinpeng.xandroidarch.ui.base

import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.ui.MainActivityTag
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity
import indi.yume.tools.fragmentmanager.BaseManagerFragment

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseActivity : BaseFragmentManagerActivity() {

    override fun fragmentViewId() = R.id.fragment_layout

    override fun baseFragmentWithTag(): MutableMap<String, Class<out BaseManagerFragment>> = MainActivityTag.toMap()


}