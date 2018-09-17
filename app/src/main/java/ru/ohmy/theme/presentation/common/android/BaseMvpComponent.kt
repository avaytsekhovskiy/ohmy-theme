package ru.ohmy.theme.presentation.common.android


import com.arellomobile.mvp.MvpDelegate
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.core.ext.optionalLazy
import ru.ohmy.theme.presentation.common.mvp.delegate.DebugDelegate
import ru.ohmy.theme.presentation.common.mvp.delegate.DialogDelegate
import ru.ohmy.theme.presentation.common.mvp.delegate.KeyboardDelegate
import ru.ohmy.theme.presentation.common.mvp.delegate.ToastDelegate
import ru.ohmy.theme.presentation.common.mvp.view.BaseView
import ru.ohmy.theme.presentation.common.mvp.view.message.DialogView
import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView
import ru.ohmy.theme.presentation.common.mvp.view.ui.KeyboardView

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class BaseMvpComponent(
        protected val activity: BaseActivity,
        parentDelegate: MvpDelegate<*> = activity.mvpDelegate,
        val dialogDelegate: DialogDelegate = activity.dialogDelegate,
        val keyboardDelegate: KeyboardDelegate = activity.keyboardDelegate,
        val toastDelegate: ToastDelegate = activity.toastDelegate
) : BaseView,
        DialogView by dialogDelegate,
        KeyboardView by keyboardDelegate,
        ToastView by toastDelegate {

    val log by logger()

    val debugDelegate by lazy { DebugDelegate(toastDelegate, log) }

    private var mvpDelegate by optionalLazy {
        MvpDelegate(this).apply { setParentDelegate(parentDelegate, javaClass.simpleName) }
    }

    open fun onCreate() {
        mvpDelegate.onCreate()
    }

    override fun showDebugMessage(comment: CharSequence, error: Throwable?) = debugDelegate.showDebugMessage(comment, error)

}
