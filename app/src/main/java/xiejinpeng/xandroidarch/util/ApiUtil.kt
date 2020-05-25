package xiejinpeng.xandroidarch.util

import android.content.Context
import java.io.IOException

object ApiUtil {
    fun parseErrorMsg(c: Context, t: Throwable): Pair<String, String> {
        return when (t) {
            is IOException -> "" to ""
            else -> "" to ""
        }
    }
}