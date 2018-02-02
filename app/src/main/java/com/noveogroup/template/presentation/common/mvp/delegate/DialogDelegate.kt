package com.noveogroup.template.presentation.common.mvp.delegate

import android.content.Context
import android.support.v7.app.AlertDialog

import com.noveogroup.template.presentation.common.mvp.view.message.DialogView

@Suppress("MemberVisibilityCanPrivate", "MemberVisibilityCanBePrivate")
class DialogDelegate(private val context: Context) : DialogView {

    private var singleDialog: AlertDialog? = null

    override fun showSimpleDialog(title: String, description: String) {
        val builder = with(AlertDialog.Builder(context)) {
            setTitle(title)
            setMessage(description)
            setPositiveButton("OK") { _, _ -> cancelDialog() }
        }
        showDialog(builder.create())
    }

    override fun cancelDialog() {
        singleDialog?.takeIf { it.isShowing }?.apply { dismiss() }
        singleDialog = null
    }

    fun showDialog(alertDialog: AlertDialog) {
        cancelDialog()
        singleDialog = alertDialog.apply { show() }
    }
}
