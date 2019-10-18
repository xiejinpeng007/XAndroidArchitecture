package xiejinpeng.xandroidarch.ui.home_02

import android.text.TextUtils
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.FragmentHome02Binding
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.ui.base.BindingFragment

class Home02Fragment : BindingFragment<FragmentHome02Binding, Home02ViewModel>(
    Home02ViewModel::class.java, layoutRes = R.layout.fragment_home_02
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