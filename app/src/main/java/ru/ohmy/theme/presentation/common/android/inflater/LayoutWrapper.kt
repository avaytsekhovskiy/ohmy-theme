package ru.ohmy.theme.presentation.common.android.inflater

import android.support.annotation.LayoutRes

import ru.ohmy.theme.R

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class LayoutWrapper(
        @LayoutRes val contentId: Int,
        @LayoutRes val noContentId: Int = R.layout.include_simple_text,
        @LayoutRes val errorId: Int = R.layout.include_simple_text
)
