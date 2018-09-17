package ru.ohmy.theme.presentation.common.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.ohmy.theme.core.ext.debugName
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.core.rx.RxHelper
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
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

    override fun attachView(view: View?) {
        super.attachView(view)
        log.debug("attached ${view?.debugName}")
    }

    override fun detachView(view: View?) {
        super.detachView(view)
        log.debug("detached ${view?.debugName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        rxHelper.unsubscribeAll()
    }

    open fun back() = globalRouter.defaultExit()

    protected fun Disposable.unsubscribeOnDestroy() = rxHelper.add(this)

}
