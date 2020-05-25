package xiejinpeng.xandroidarch.ui.base

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import xiejinpeng.xandroidarch.util.BindLife
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import xiejinpeng.xandroidarch.util.ApiErrorLiveEvent
import xiejinpeng.xandroidarch.util.catchApiError

/**
 * Created by xiejinpeng on 2018/2/8.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app),
    LifecycleObserver,
    BindLife,
    KodeinAware {

    override val kodein by kodein()
    override val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    //state
    val progressDialog = MutableLiveData(false)
    var vmInit = false

    //event
    val apiErrorEvent = ApiErrorLiveEvent()
    val dialogEvent = DialogLiveEvent()

    /**
     *  auto handle ApiError while API Request
     */
    protected fun <T> Single<T>.catchApiError(): Single<T> = catchApiError(apiErrorEvent)
    protected fun <T> Observable<T>.catchApiError(): Observable<T> = catchApiError(apiErrorEvent)
    protected fun Completable.catchApiError(): Completable = catchApiError(apiErrorEvent)

    /**
     *  auto handle ProgressDialog display logic for API Request
     */

    protected fun <T> Single<T>.autoProgressDialog(): Single<T> = autoProgressDialog(progressDialog)
    protected fun Completable.autoProgressDialog(): Completable = autoProgressDialog(progressDialog)

    fun <T> Single<T>.autoProgressDialog(progressDialog: MutableLiveData<Boolean>): Single<T> =
        compose {
            it
                .doOnSubscribe { progressDialog.showProgressDialog() }
                .doOnDispose { progressDialog.hideProgressDialog() }
                .doOnError { progressDialog.hideProgressDialog() }
                .doOnSuccess { progressDialog.hideProgressDialog() }
        }

    fun Completable.autoProgressDialog(progressDialog: MutableLiveData<Boolean>): Completable =
        compose {
            it
                .doOnSubscribe { progressDialog.showProgressDialog() }
                .doOnDispose { progressDialog.hideProgressDialog() }
                .doOnError { progressDialog.hideProgressDialog() }
                .doOnComplete { progressDialog.hideProgressDialog() }
        }

    private fun MutableLiveData<Boolean>.showProgressDialog() = this.postValue(true)
    private fun MutableLiveData<Boolean>.hideProgressDialog() = this.postValue(false)


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}