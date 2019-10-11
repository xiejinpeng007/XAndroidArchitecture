package xiejinpeng.xandroidarch.model.sharedpref

import com.chibatching.kotpref.KotprefModel
import xiejinpeng.xandroidarch.util.Constants

/**
 * Created by xiejinpeng on 2018/2/8.
 */
object SharedPrefModel : KotprefModel() {
    override val kotprefName: String = Constants.SHARED_PREF_FILE_NAME

}