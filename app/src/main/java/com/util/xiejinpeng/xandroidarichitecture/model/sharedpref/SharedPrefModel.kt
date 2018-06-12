package com.util.xiejinpeng.xandroidarichitecture.model.sharedpref

import com.chibatching.kotpref.KotprefModel
import com.util.xiejinpeng.xandroidarichitecture.util.Constants

/**
 * Created by xiejinpeng on 2018/2/8.
 */
object SharedPrefModel : KotprefModel() {
    override val kotprefName: String = Constants.SHARED_PREF_FILE_NAME

}