package com.noveogroup.template.presentation.common.mvp.delegate


import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

import com.noveogroup.template.presentation.common.mvp.view.message.ToastView

class ToastDelegate(private val context: Context) : ToastView {

    private var singleToast: Toast? = null

    override fun showToast(text: CharSequence) {
        cancelToast()
        singleToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
                .apply { show() }
    }

    override fun showToast(@StringRes stringId: Int, vararg args: Any) =
            showToast(context.getString(stringId, *args))

    override fun cancelToast() =
            singleToast?.cancel() ?: Unit

}
