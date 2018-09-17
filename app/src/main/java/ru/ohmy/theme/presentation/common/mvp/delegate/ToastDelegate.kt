package ru.ohmy.theme.presentation.common.mvp.delegate


import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import ru.ohmy.theme.presentation.common.ext.toast

import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView

class ToastDelegate(private val context: Context) : ToastView {

    private var singleToast: Toast? = null

    override fun showToast(text: CharSequence) {
        cancelToast()
        singleToast = context.toast(text)
    }

    override fun showToast(@StringRes stringId: Int, vararg args: Any) {
        showToast(context.getString(stringId, *args))
    }

    override fun cancelToast() {
        singleToast?.cancel()
    }

}
