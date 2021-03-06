package ru.ohmy.theme.presentation.common.android.widget

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

@Suppress("unused")
class TouchConsumerFrameLayout : FrameLayout {

    @Suppress("MemberVisibilityCanBePrivate")
    var consumeTouches = true

    var silentListener: ((ev: MotionEvent) -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        silentListener?.invoke(ev)
        return consumeTouches || super.dispatchTouchEvent(ev)
    }
}
