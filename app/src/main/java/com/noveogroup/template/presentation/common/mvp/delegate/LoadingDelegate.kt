package com.noveogroup.template.presentation.common.mvp.delegate

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.presentation.common.ext.*
import com.noveogroup.template.presentation.common.mvp.view.ui.LoadingView
import fr.castorflex.android.circularprogressbar.CircularProgressBar
import org.slf4j.Logger

@Suppress("MemberVisibilityCanPrivate", "MemberVisibilityCanBePrivate")
class LoadingDelegate(
        override val container: View,
        private val log: Logger = LoadingDelegate.log
) : LoadingView, ViewBinder {

    val contentFrame: FrameLayout?      by bindOptionalView(R.id.wrapperContentContainer)
    val emptyContentFrame: FrameLayout? by bindOptionalView(R.id.wrapperEmptyContainer)
    val errorFrame: FrameLayout?        by bindOptionalView(R.id.wrapperErrorContainer)
    val maskFrame: FrameLayout?         by bindOptionalView(R.id.wrapperMask)
    val progress: CircularProgressBar?  by bindOptionalView(R.id.wrapperProgress)

    constructor(activity: Activity, log: Logger = LoadingDelegate.log) : this(
            container = activity.findViewById(android.R.id.content),
            log = log
    )

    override fun showRegularContent() {
        log.info("showRegularContent")
        showViews(contentFrame)
        hideViews(emptyContentFrame, errorFrame)
    }

    override fun showErrorContent() {
        log.info("showErrorContent")
        showViews(errorFrame)
        hideViews(contentFrame, emptyContentFrame)
    }

    override fun showEmptyContent() {
        log.info("showEmptyContent")
        showViews(emptyContentFrame)
        hideViews(contentFrame, errorFrame)
    }

    override fun hideContent() {
        log.info("hideContent")
        hideViews(contentFrame, emptyContentFrame, errorFrame)
    }

    override fun showProgressWithMask() {
        log.info("showProgressWithMask")
        showViews(progress, maskFrame)
    }

    override fun hideProgressWithMask() {
        log.info("hideProgressWithMask")
        hideViews(progress, maskFrame)
    }

    fun unbind() = ButterKnife.reset(this)

    companion object {
        val log by logger("LoadingDelegate")
    }
}
