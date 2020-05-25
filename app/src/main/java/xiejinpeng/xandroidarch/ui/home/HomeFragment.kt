package xiejinpeng.xandroidarch.ui.home

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentHomeBinding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    HomeViewModel::class.java, layoutRes = R.layout.fragment_home
) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initView() {
    }

    override fun initData() {

    }

    override fun observe() {

    }
}