package xiejinpeng.xandroidarch.util

import java.io.IOException

object ApiUtil {
    fun parseError2Text(t: Throwable): Pair<String, String> {
        return when (t) {
            is IOException -> "" to ""
            else -> "" to ""
        }
    }
}