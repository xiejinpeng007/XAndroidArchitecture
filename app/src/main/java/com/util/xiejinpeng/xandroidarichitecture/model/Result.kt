package com.util.xiejinpeng.xandroidarichitecture.model

/**
 * Created by xiejinpeng on 2018/2/12.
 */

sealed class Result {

    data class Success<T>(val data: T) : Result()

    data class Failure(val errorMsg: String?, val e: Throwable) : Result()

}