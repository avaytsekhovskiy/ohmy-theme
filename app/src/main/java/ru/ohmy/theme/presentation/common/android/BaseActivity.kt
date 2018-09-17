package ru.ohmy.theme.presentation.common.android

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import ru.ohmy.theme.R
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.android.helper.orientation.ActivityOrientation
import ru.ohmy.theme.presentation.common.android.helper.orientation.OrientationHelper
import ru.ohmy.theme.presentation.common.ext.contentFrom
import ru.ohmy.theme.presentation.common.ext.inflater
import ru.ohmy.theme.presentation.common.mvp.delegate.*
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
import ru.ohmy.theme.presentation.common.navigation.BackListener
import ru.ohmy.theme.presentation.common.navigation.NavigatorLifecycle
import ru.ohmy.theme.presentation.di.ScopeInitializer
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

    /* DELEGATES */
    lateinit var toastDelegate: ToastDelegate
    lateinit var dialogDelegate: DialogDelegate
    lateinit var keyboardDelegate: KeyboardDelegate
    lateinit var loadingDelegate: LoadingDelegate
    lateinit var debugDelegate: DebugDelegate

    @Inject
    lateinit var themeProxy: ThemeProxy

    open val themeId by lazy {
        themeProxy.read()
                .let {
                    when (it) {
                        Theme.BASE_LIGHT -> R.style.AppTheme_Light
                        Theme.BASE_DARK -> R.style.AppTheme_Dark
                        Theme.GREEN_LIGHT -> R.style.AppTheme_Light_Green
                        Theme.GREEN_DARK -> R.style.AppTheme_Dark_Green
                    }
                }
                .also { log.warn("themeId = $it") }
    }

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

        /* MVP */
        mvpDelegate.onCreate(savedInstanceState)

        /* Navigation */
        navigatorLifecycle = NavigatorLifecycle(this, globalRouter, orientationHelper)

        savedInstanceState?.let(::tryRestoreState)
    }

    override fun onStart() {
        super.onStart()

        tryAttachMvp()

        log.debug("started")
    }

    override fun onResume() {
        super.onResume()

        tryAttachMvp()

        log.debug("resumed")
        navigatorLifecycle.onResume()
    }

    override fun onPause() {
        super.onPause()
        log.debug("paused")
        navigatorLifecycle.onPause()
        orientationHelper.onPause()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onDetach()

        log.debug("stopped")
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
