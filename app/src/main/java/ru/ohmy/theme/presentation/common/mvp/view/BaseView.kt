package ru.ohmy.theme.presentation.common.mvp.view

import ru.ohmy.theme.presentation.common.mvp.view.message.DebugView
import ru.ohmy.theme.presentation.common.mvp.view.message.DialogView
import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView
import ru.ohmy.theme.presentation.common.mvp.view.ui.KeyboardView

/**
 * Typical Fragment or Activity.
 * But if you need custom setup - enumerate extends Views explicitly
 */
interface BaseView : DebugView, ToastView, DialogView, KeyboardView
