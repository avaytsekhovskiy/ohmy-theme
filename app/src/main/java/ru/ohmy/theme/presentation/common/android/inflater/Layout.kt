package ru.ohmy.theme.presentation.common.android.inflater

import android.support.annotation.LayoutRes

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class Layout(
        @LayoutRes val contentId: Int
)
