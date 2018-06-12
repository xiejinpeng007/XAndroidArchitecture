package com.util.xiejinpeng.xandroidarichitecture.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.util.xiejinpeng.xandroidarichitecture.R
import com.util.xiejinpeng.xandroidarichitecture.databinding.ActivityMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : BaseActivity(), KodeinAware {
    //retrieving the Application level kodein by closestKodein func
    //of course you can define the Activity level kodein extend that
    override val kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }
}
