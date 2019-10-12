package xiejinpeng.xandroidarch.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.LongDef
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import xiejinpeng.xandroidarch.R
import java.util.concurrent.atomic.AtomicInteger

object DialogUtil {
    const val BUTTON_TYPE_CANCEL = 0x1L
    const val BUTTON_TYPE_OK = 0x1L shl 1
    const val BUTTON_TYPE_RETRY = 0x1L shl 2
    const val BUTTON_TYPE_DISMISS = 0x1L shl 3
    const val BUTTON_TYPE_CLOSE = 0x1L shl 4

    private var progressDialog: Dialog? = null
    private val dialogCount = AtomicInteger(0)
    private val dialogContainer by lazy { mutableSetOf<AlertDialog>() }

    @JvmOverloads
    fun showProgressDialogNow(context: Context?, isCancelable: Boolean = false): Dialog? {
        val dialog = createFullScreenDialog(
            context,
            R.layout.loading_layout,
            true,
            isCancelable
        )
        dialog?.show()
        return dialog
    }

    @JvmOverloads
    fun createFullScreenDialog(
        context: Context?,
        @LayoutRes layout: Int,
        clearBackground: Boolean = false,
        isCancelable: Boolean = false
    ): Dialog? {
        if (context == null) return null
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        if (window != null) {
            if (clearBackground)
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(layout)
        dialog.setCancelable(isCancelable)

        return dialog
    }

    fun <T> autoProgressDialog(context: Context): SingleTransformer<T, T> {
        return SingleTransformer {
            it
                .doOnSubscribe { showProgressDialog(context) }
                .doOnDispose { hideProgressDialog() }
                .doOnError { hideProgressDialog() }
                .doOnSuccess { hideProgressDialog() }
        }
    }

    fun <T> autoProgressDialogObs(context: Context): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it
                .doOnSubscribe { showProgressDialog(context) }
                .doOnDispose { hideProgressDialog() }
                .doOnError { hideProgressDialog() }
                .doOnComplete { hideProgressDialog() }
        }
    }

    fun autoProgressDialogCompletable(context: Context): CompletableTransformer {
        return CompletableTransformer {
            it
                .doOnSubscribe { showProgressDialog(context) }
                .doOnDispose { hideProgressDialog() }
                .doOnError { t -> hideProgressDialog() }
                .doOnComplete { hideProgressDialog() }
        }
    }

    fun <T> autoProgressDialogMaybe(context: Context): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it
                .doOnSubscribe { showProgressDialog(context) }
                .doOnDispose { hideProgressDialog() }
                .doOnError { hideProgressDialog() }
                .doOnComplete { hideProgressDialog() }
        }
    }

    private fun showProgressDialog(context: Context?, isCancelable: Boolean = false) {
        if (progressDialog != null)
            return
        if (context == null) {
            dialogCount.incrementAndGet()
            return
        }
        AndroidSchedulers.mainThread().createWorker().schedule {
            if (progressDialog == null && dialogCount.get() == 0) {
                progressDialog = showProgressDialogNow(context, isCancelable)
            }
            dialogCount.incrementAndGet()
        }
    }

    /**
     * May not going to dismiss the ProgressDialog if dialogCount > 1.
     */

    private fun hideProgressDialog() {
        AndroidSchedulers.mainThread().createWorker().schedule {
            if (dialogCount.get() > 0)
                dialogCount.decrementAndGet()
            if (progressDialog != null && dialogCount.get() == 0) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        }
    }

    /**
     * Force to dismiss the ProgressDialog with ignoring the dialogCount.
     */

    private fun hideAllProgressDialog() {
        AndroidSchedulers.mainThread().createWorker().schedule {
            if (dialogCount.get() > 0) {
                dialogCount.set(0)
                progressDialog!!.dismiss()
                progressDialog = null
            }
        }
    }

    @LongDef(
        BUTTON_TYPE_CANCEL,
        BUTTON_TYPE_OK,
        BUTTON_TYPE_RETRY,
        BUTTON_TYPE_DISMISS,
        BUTTON_TYPE_CLOSE
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ButtonType

    fun showDialogSingle(
        context: Context,
        title: String = "",
        msg: String = "",
        @StringRes positiveButtonText: Int = R.string.dialog_ok,
        @StringRes negativeButtonText: Int = R.string.dialog_cancel,
        @ButtonType positiveButton: Long = BUTTON_TYPE_OK,
        @ButtonType negativeButton: Long = BUTTON_TYPE_CANCEL
    ): Single<DialogEvent> {
        return Single.create<DialogEvent> { emitter ->
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveButtonText)
                { _, _ -> emitter.onSuccess(DialogEvent(button = positiveButton)) }
                .setNegativeButton(negativeButtonText)
                { _, _ -> emitter.onSuccess(DialogEvent(button = negativeButton)) }
                .show()
        }
    }

    //saveState to Container in order to dismiss
    private fun AlertDialog.saveState(): AlertDialog {
        dialogContainer.add(this)
        this.setOnDismissListener { dialogContainer.remove(this) }
        return this
    }

    //dismiss all dialogs if bindLife
    fun dismissAllDialogs() {
        dialogContainer.apply {
            forEach { it.dismiss() }
            clear()
        }
    }

    data class DialogEvent(
        val dialog: DialogInterface? = null,
        @ButtonType
        val button: Long = 0
    )
}