package com.noveogroup.template.presentation.common.mvp.delegate

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.noveogroup.template.presentation.common.mvp.view.ui.KeyboardView

class KeyboardDelegate(
        private val activity: Activity
) : KeyboardView {

    private val input: InputMethodManager
        get() = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun showKeyboard(target: Any): Unit = with(input) {
        when (target) {
            is View -> {
                showSoftInput(target.apply { requestFocus() }, InputMethodManager.SHOW_FORCED)
            }
            else -> toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    override fun hideKeyboard() {
        activity.currentFocus?.apply { input.hideSoftInputFromWindow(windowToken, 0) }
    }

}
