package com.util.xiejinpeng.xandroidarichitecture.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.util.xiejinpeng.xandroidarichitecture.R
import com.util.xiejinpeng.xandroidarichitecture.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }
}
