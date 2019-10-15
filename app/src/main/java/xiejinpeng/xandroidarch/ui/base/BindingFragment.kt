package xiejinpeng.xandroidarch.ui.base

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import indi.yume.tools.fragmentmanager.OnShowMode

/**
 * Created by xiejinpeng on 2018/2/8.
 */

abstract class BindingFragment<Bind : ViewDataBinding, VM : ViewModel>
constructor(
    private val clazz: Class<VM>,
    private val bindingCreator: (LayoutInflater, ViewGroup?) -> Bind
) : BaseLifecycleFragment() {

    constructor(clazz: Class<VM>, @LayoutRes layoutRes: Int) : this(clazz, { inflater, group ->
        DataBindingUtil.inflate(inflater, layoutRes, group, false)
    })

    val viewModel: VM by lazy {
        ViewModelProviders.of(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(clazz)
    }

    private lateinit var binding: Bind

    //method

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = bindingCreator.invoke(layoutInflater, container)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    protected fun renderView(render: (Bind) -> (Unit)) {
        render.invoke(binding)
    }

//    override fun onShow(callMode: Int) {
//        super.onShow(callMode)
////        Note :this Mode will not call if fragment is first of the stack
//        if (callMode == OnShowMode.ON_CREATE_AFTER_ANIM) {
//            initViewAfterAnim()
//            initDataAfterAnim()
//        }
//    }

    open fun initDataAfterAnim() {

    }

    open fun initViewAfterAnim() {

    }

    //ext

    protected fun <T> LiveData<T>.observe(observer: (T?) -> Unit) where T : Any =
        observe(this@BindingFragment, Observer<T> { v -> observer(v) })

    protected fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit) {
        this.observe(this@BindingFragment, Observer {
            if (it != null) {
                observer(it)
            }
        })
    }
}
