package xiejinpeng.xandroidarch.ui.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentHomeBinding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding, HomeViewModel>(
    HomeViewModel::class.java, layoutRes = R.layout.fragment_home
) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initView() {

        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel
        binding.moveToNextButton.setOnClickListener { findNavController().navigate(R.id.home02Fragment) }

        renderView { binding ->
//            sharedViewModel.sharedData.observe { binding.sharedViewModel?.sharedData?.value = it }
        }
    }


    override fun initData() {

    }
}