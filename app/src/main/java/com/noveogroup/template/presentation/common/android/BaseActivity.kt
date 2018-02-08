package com.noveogroup.template.presentation.common.android

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.app.Fragment
import android.support.v4.app.SupportFragmentUtils.cleanSupportLibraryIssues
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.android.helper.DebugDrawerHelper
import com.noveogroup.template.presentation.common.android.helper.orientation.ActivityOrientation
import com.noveogroup.template.presentation.common.android.helper.orientation.OrientationHelper
import com.noveogroup.template.presentation.common.ext.contentFrom
import com.noveogroup.template.presentation.common.ext.inflater
import com.noveogroup.template.presentation.common.mvp.delegate.*
import com.noveogroup.template.presentation.common.mvp.view.BaseView
import com.noveogroup.template.presentation.common.navigation.BackListener
import com.noveogroup.template.presentation.common.navigation.NavigatorLifecycle
import com.noveogroup.template.presentation.di.ScopeInitializer
import io.palaima.debugdrawer.DebugDrawer
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseActivity : AppCompatActivity(), BaseView, UniqueIdentifiable {

    val log by logger()

    /* UniqueIdentifiable */
    final override val uniqueIdReference = AtomicReference<String?>()

    /* MVP */
    open val orientationHelper by lazy { OrientationHelper(this, ActivityOrientation.BOTH) }
    val mvpDelegate by lazy { MvpDelegate(this) }
    val rotating get() = orientationHelper.rotating

    /* DI */
    abstract val lazyScope: ScopeInitializer

    /* NAVIGATION */
    @Inject
    lateinit var globalRouter: GlobalRouter
    lateinit var navigatorLifecycle: NavigatorLifecycle

    /* SUPPORT */
    val handler by lazy { Handler() }
    private val cleanSupportLibraryIssuesTask = { cleanSupportLibraryIssues() }

    /* DELEGATES */
    lateinit var toastDelegate: ToastDelegate
    lateinit var dialogDelegate: DialogDelegate
    lateinit var keyboardDelegate: KeyboardDelegate
    lateinit var loadingDelegate: LoadingDelegate
    lateinit var debugDelegate: DebugDelegate

    /* VIEW */
    @Inject
    lateinit var debugHelper: DebugDrawerHelper
    private lateinit var debugDrawer: DebugDrawer
    @StyleRes
    open val themeId: Int = R.style.AppTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.info("created")

        /* DI */
        lazyScope.install()
        lazyScope.inject(this)

        /* UI */
        setTheme(themeId)
        setContentView(inflater.contentFrom(this))

        /* Orientation */
        orientationHelper.onCreate()

        /* Delegates (with UI) */
        toastDelegate = ToastDelegate(this)
        dialogDelegate = DialogDelegate(this)
        keyboardDelegate = KeyboardDelegate(this)
        loadingDelegate = LoadingDelegate(this, log)
        debugDelegate = DebugDelegate(toastDelegate, log)

        /* Debug (with UI) */
        debugDrawer = debugHelper.makeDrawer(this)

        /* MVP */
        mvpDelegate.onCreate(savedInstanceState)

        /* Navigation */
        navigatorLifecycle = NavigatorLifecycle(this, globalRouter, orientationHelper)

        savedInstanceState?.let(::tryRestoreState)
        handler.post(cleanSupportLibraryIssuesTask.apply { invoke() })
    }

    override fun onStart() {
        super.onStart()

        tryAttachMvp()

        log.debug("started")
        debugDrawer.onStart()
    }

    override fun onResume() {
        super.onResume()

        tryAttachMvp()

        log.debug("resumed")
        debugDrawer.onResume()
        navigatorLifecycle.onResume()
    }

    override fun onPause() {
        super.onPause()
        log.debug("paused")
        debugDrawer.onPause()
        navigatorLifecycle.onPause()
        orientationHelper.onPause()

        handler.removeCallbacks(cleanSupportLibraryIssuesTask)
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onDetach()

        log.debug("stopped")
        debugDrawer.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        log.info("destroyed")
        mvpDelegate.onDestroyView()
        if (isFinishing) {
            mvpDelegate.onDestroy()
            lazyScope.close()
        }
    }

    open fun tryAttachMvp() {
        mvpDelegate.takeUnless { rotating }?.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()

        saveUniqueId(outState)
        log.debug("state saved")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let(::tryRestoreState)
    }

    /**
     * common logic of savedInstanceState parsing.
     * This method called twice:
     * - [BaseActivity.onCreate] if savedInstanceState not null.
     * - [BaseActivity.tryRestoreState] if savedInstanceState not null.
     */
    protected open fun tryRestoreState(savedInstanceState: Bundle) = with(savedInstanceState) {
        log.debug("state restored")
        restoreUniqueId(this)
    }

    override fun attachBaseContext(newBase: Context) =
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    /* DELEGATED METHODS */

    override fun showToast(text: CharSequence) =
            toastDelegate.showToast(text)

    override fun showToast(@StringRes stringId: Int, vararg args: Any) =
            toastDelegate.showToast(stringId, *args)

    override fun cancelToast() =
            toastDelegate.cancelToast()

    override fun showDebugMessage(comment: CharSequence, error: Throwable?) =
            debugDelegate.showDebugMessage(comment, error)

    override fun hideKeyboard() =
            keyboardDelegate.hideKeyboard()

    override fun showKeyboard(target: Any) =
            keyboardDelegate.showKeyboard(target)

    override fun showSimpleDialog(title: String, description: String) =
            dialogDelegate.showSimpleDialog(title, description)

    override fun cancelDialog() =
            dialogDelegate.cancelDialog()

    fun showRegularContent() =
            loadingDelegate.showRegularContent()

    fun showErrorContent() =
            loadingDelegate.showErrorContent()

    fun showEmptyContent() =
            loadingDelegate.showEmptyContent()

    fun hideContent() =
            loadingDelegate.hideContent()

    fun showProgressWithMask() =
            loadingDelegate.showProgressWithMask()

    fun hideProgressWithMask() =
            loadingDelegate.hideProgressWithMask()

    protected fun processBackIfListener(fragment: Fragment?) = when (fragment) {
        is BackListener -> fragment.onBackPressed()
        else -> false
    }

    protected fun Fragment.addTo(container: ViewGroup) = addTo(container.id)

    protected fun Fragment.addTo(@IdRes containerId: Int) = supportFragmentManager.let {
        it.beginTransaction().replace(containerId, this).commit()
        it.executePendingTransactions()
    }

}
