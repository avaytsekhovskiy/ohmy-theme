package com.noveogroup.template.presentation.common.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller


class NonSwipeableViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        setMyScroller()
    }

    // Never allow swiping to switch between pages
    override fun onInterceptTouchEvent(event: MotionEvent) = false

    // Never allow swiping to switch between pages
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent) = false

    //down one is added for smooth scrolling

    private fun setMyScroller() = try {
        ViewPager::class.java.getDeclaredField("mScroller").apply {
            isAccessible = true
            set(this, MyScroller(context))
        }
    } catch (ignored: Exception) {
    }

    inner class MyScroller(context: Context) : Scroller(context, DecelerateInterpolator()) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/)
        }
    }
}