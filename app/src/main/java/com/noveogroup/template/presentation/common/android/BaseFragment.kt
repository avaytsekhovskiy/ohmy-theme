package com.noveogroup.template.presentation.common.android

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.SupportFragmentUtils.enableChildFragmentManagerAccess
import android.support.v4.app.SupportFragmentUtils.updateLoaderManagerHostController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.noveogroup.debugdrawer.data.canary.LeakCanaryProxy
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.presentation.common.ext.ButterKnife
import com.noveogroup.template.presentation.common.ext.contentFrom
import com.noveogroup.template.presentation.common.mvp.delegate.*
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import com.noveogroup.template.presentation.di.DI

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class BaseFragment : Fragment(), BaseView {

    val log by logger()

    val baseActivity: BaseActivity get() = activity!! as BaseActivity
    val glide: RequestManager? get() = Glide.with(activity!!)

    val rotating get() = baseActivity.rotating

    val dialogDelegate: DialogDelegate get() = baseActivity.dialogDelegate
    val keyboardDelegate: KeyboardDelegate get() = baseActivity.keyboardDelegate
    val toastDelegate: ToastDelegate get() = baseActivity.toastDelegate
    var debugDelegate: DebugDelegate? = null
    var loadingDelegate: LoadingDelegate? = null

    // MVP
    private var fragmentStateSaved: Boolean = false
    val mvpDelegate: MvpDelegate<out BaseFragment> by lazy { MvpDelegate<BaseFragment>(this) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log.debug("attached")
        updateLoaderManagerHostController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.info("created")
        mvpDelegate.onCreate(savedInstanceState)
        savedInstanceState?.let(::tryRestoreState)
    }

    override fun onCreateView(inflater: LayoutInflater, view: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.contentFrom(this, view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log.debug("view created")
        debugDelegate = DebugDelegate(toastDelegate, log)
        loadingDelegate = LoadingDelegate(view, log)
    }

    override fun onStart() {
        super.onStart()
        log.debug("started")

        fragmentStateSaved = false
        tryAttachMvp()
    }

    override fun onResume() {
        super.onResume()
        log.debug("resumed")

        fragmentStateSaved = false
        tryAttachMvp()
    }

    override fun onPause() {
        super.onPause()
        log.debug("paused")
    }

    override fun onStop() {
        super.onStop()
        log.debug("stopped")

        mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        log.debug("view destroyed")
        loadingDelegate?.unbind()
        ButterKnife.reset(this)

        hideKeyboard()
        super.onDestroyView()

        debugDelegate = null
        loadingDelegate = null

        mvpDelegate.onDetach()
        mvpDelegate.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        log.debug("detached")
        enableChildFragmentManagerAccess()
    }

    override fun onDestroy() {
        super.onDestroy()
        log.debug("destroyed")
        releaseMvp()

        DI.appScope.getInstance(LeakCanaryProxy::class.java).watch(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log.debug("state saved")

        fragmentStateSaved = true

        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let(::tryRestoreState)
    }

    /**
     * common logic of savedInstanceState parsing.
     * This method called twice:
     * - onCreate() if savedInstanceState not null.
     * - onViewStateRestore() if savedInstanceState not null.
     */
    protected open fun tryRestoreState(savedInstanceState: Bundle) {
        log.debug("state restored")
    }

    override fun showToast(text: CharSequence) = toastDelegate.showToast(text)

    override fun showToast(@StringRes stringId: Int, vararg args: Any) = toastDelegate.showToast(stringId, *args)

    override fun cancelToast() = toastDelegate.cancelToast()

    override fun showSimpleDialog(title: String, description: String) = dialogDelegate.showSimpleDialog(title, description)

    override fun cancelDialog() = dialogDelegate.cancelDialog()

    override fun hideKeyboard() = keyboardDelegate.hideKeyboard()

    override fun showKeyboard(target: Any) = keyboardDelegate.showKeyboard(target)

    override fun showDebugMessage(comment: CharSequence, error: Throwable?) {
        debugDelegate?.showDebugMessage(comment, error)
    }

    fun showRegularContent() {
        loadingDelegate?.showRegularContent()
    }

    fun showErrorContent() {
        loadingDelegate?.showErrorContent()
    }

    fun showEmptyContent() {
        loadingDelegate?.showEmptyContent()
    }

    fun hideContent() {
        loadingDelegate?.hideContent()
    }

    fun showProgressWithMask() {
        loadingDelegate?.showProgressWithMask()
    }

    fun hideProgressWithMask() {
        loadingDelegate?.hideProgressWithMask()
    }

    /**
     * Method for handling fragment specific back press event.
     *
     * @return boolean (true - stop handling, false - continue handling)
     */
    open fun onBackPressed() = false

    protected fun performBackPress() = baseActivity.onBackPressed()

    open fun tryAttachMvp() {
        mvpDelegate.takeUnless { rotating }?.onAttach()
    }

    private fun releaseMvp() {
        //We leave the screen and respectively all fragments will be destroyed
        if (baseActivity.isFinishing) {
            mvpDelegate.onDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (fragmentStateSaved) {
            fragmentStateSaved = false
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            mvpDelegate.onDestroy()
        }
    }

    protected fun Fragment.addTo(@IdRes containerId: Int) = childFragmentManager.let {
        it.beginTransaction().replace(containerId, this).commit()
        it.executePendingTransactions()
    }
}
