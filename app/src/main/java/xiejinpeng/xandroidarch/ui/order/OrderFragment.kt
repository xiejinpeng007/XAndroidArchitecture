package xiejinpeng.xandroidarch.ui.order

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentOrderBinding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BaseFragment

class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(
    OrderViewModel::class.java, layoutRes = R.layout.fragment_order
) {

    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initView() {

    }


    override fun initData() {

    }

    override fun observe() {

    }
}