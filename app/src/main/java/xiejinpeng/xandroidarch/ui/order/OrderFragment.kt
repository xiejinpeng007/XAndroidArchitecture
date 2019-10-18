package xiejinpeng.xandroidarch.ui.order

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentOrderBinding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BindingFragment

class OrderFragment : BindingFragment<FragmentOrderBinding, OrderViewModel>(
    OrderViewModel::class.java, layoutRes = R.layout.fragment_order
) {

    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initView() {

        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel

        binding.moveToNextButton
            .setOnClickListener { findNavController().navigate(R.id.order02Fragment) }

    }


    override fun initData() {

    }
}