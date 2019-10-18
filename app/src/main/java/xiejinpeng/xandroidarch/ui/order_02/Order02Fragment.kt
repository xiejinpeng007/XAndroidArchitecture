package xiejinpeng.xandroidarch.ui.order_02

import android.text.TextUtils
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentOrder02Binding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BindingFragment

class Order02Fragment : BindingFragment<FragmentOrder02Binding, Order02ViewModel>(
    Order02ViewModel::class.java, layoutRes = R.layout.fragment_order_02
) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initView() {
        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel
        binding.plusOneButton.setOnClickListener {
            sharedViewModel.sharedData.value = sharedViewModel.sharedData.value?.plus(1)
        }
        renderView { binding ->
        }
    }


    override fun initData() {

    }
}