package xiejinpeng.xandroidarch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import indi.yume.tools.fragmentmanager.BaseManagerFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import timber.log.Timber
import xiejinpeng.xandroidarch.util.*
import java.lang.ClassCastException

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseLifecycleFragment : Fragment(),
    BindLife,
    FragmentLifecycleOwner {

    override val compositeDisposable = CompositeDisposable()

    override val lifeSubject: Subject<FragmentLifeEvent> =
        FragmentLifecycleOwner.defaultLifeSubject()

    /**
     * addition function
     * use this fun to listen Activity's lifecycle by the returned Observable
     *
     * bindActivityLife()
     * .ofType<ActivityLifeEvent.OnResume>()
     * .doOnComplete{}
     *
     */

    @Throws(ClassCastException::class)
    fun bindActivityLife(): Observable<ActivityLifeEvent> =
        (context as ActivityLifecycleOwner).bindActivityLife()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeState(FragmentLifeEvent.OnCreate(this, savedInstanceState))

        Timber.i("============== ${javaClass.simpleName} onCreated() ==============")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        makeState(FragmentLifeEvent.OnCreateView(this, inflater, container, savedInstanceState))
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeState(FragmentLifeEvent.OnViewCreated(this, view, savedInstanceState))
    }


    override fun onStart() {
        super.onStart()
        makeState(FragmentLifeEvent.OnStart(this))
    }

    override fun onResume() {
        super.onResume()
        makeState(FragmentLifeEvent.OnResume(this))
    }

//    override fun onShow(callMode: Int) {
//        super.onShow(callMode)
//        makeState(FragmentLifeEvent.OnShow(this, OnShowMode.fromMode(callMode)))
//    }

//    override fun onHide(hideMode: Int) {
//        super.onHide(hideMode)
//        makeState(FragmentLifeEvent.OnHide(this, OnHideMode.fromMode(hideMode)))
//    }

    override fun onDestroy() {
        super.onDestroy()

        makeState(FragmentLifeEvent.OnDestroy(this))
        destroyLifecycle()
        destroyDisposable()
    }

//    override fun finish() {
//        activity?.hideSoftKeyBoard()
//        super.finish()
//    }

}