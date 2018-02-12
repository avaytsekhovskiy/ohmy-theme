package com.noveogroup.template.presentation.common.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.core.ext.trackBy
import com.noveogroup.template.core.rx.RxHelper
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import io.reactivex.disposables.Disposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePresenter<View : MvpView>(
        val globalRouter: GlobalRouter
) : MvpPresenter<View>() {

    val log by logger()
    val rxHelper by lazy { RxHelper() }

    init {
        log.info("constructed")
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        log.info("initialized")
    }

    override fun onDestroy() {
        super.onDestroy()
        rxHelper.unsubscribeAll()
    }

    open fun back() = globalRouter.defaultExit()

    protected fun Disposable.unsubscribeOnDestroy() = trackBy(rxHelper)

}
