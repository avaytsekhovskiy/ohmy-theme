package com.noveogroup.template.presentation.common.mvp.view

import com.noveogroup.template.presentation.common.mvp.view.message.DebugView
import com.noveogroup.template.presentation.common.mvp.view.message.DialogView
import com.noveogroup.template.presentation.common.mvp.view.message.ToastView
import com.noveogroup.template.presentation.common.mvp.view.ui.KeyboardView

/**
 * Typical Fragment or Activity.
 * But if you need custom setup - enumerate extends Views explicitly
 */
interface BaseView : DebugView, ToastView, DialogView, KeyboardView
