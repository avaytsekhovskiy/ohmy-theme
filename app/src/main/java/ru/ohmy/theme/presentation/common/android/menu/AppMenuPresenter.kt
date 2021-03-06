package ru.ohmy.theme.presentation.common.android.menu

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.debugdrawer.data.theme.Theme
import ru.ohmy.theme.R
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.domain.interactor.ThemeInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenStateDiffHelper
import ru.ohmy.theme.domain.interactor.state.model.ScreenState
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.common.mvp.ScreenStateListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class AppMenuPresenter @Inject constructor(
        private val themeInteractor: ThemeInteractor,
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<AppMenuView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()

        observeAppearance().unsubscribeOnDestroy()
    }

    override fun attachView(view: AppMenuView?) {
        super.attachView(view)
        viewState.selectChoice(themeInteractor.currentTheme)
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(viewState) {
        helper.ifSideModeChanged { aside ->
            when {
                aside.locked && aside.opened -> lockOpened()
                aside.locked -> lockClosed()
                aside.opened -> open().also { unlock() }
                else -> close().also { unlock() }
            }
        }
    }

    fun changeTheme(choiceId: Int) {
        delayAction(100) { closeMenu() }
        delayAction(400) {
            val theme = when (choiceId) {
                R.id.choiceBaseLight -> Theme.BASE_LIGHT
                R.id.choiceBaseDark -> Theme.BASE_DARK
                R.id.choiceGreenLight -> Theme.GREEN_LIGHT
                R.id.choiceGreenDark -> Theme.GREEN_DARK
                else -> Theme.BASE_LIGHT
            }
            if (themeInteractor.currentTheme != theme) {
                themeInteractor.changeTheme(theme)
            }
        }
    }

    fun openMenu() = toggle(true)
            .also { log.debug("on menu opened") }

    fun closeMenu() = toggle(false)
            .also { log.debug("on menu closed") }

    private fun delayAction(delayMs: Long, action: () -> Unit) {

        Observable.timer(delayMs, TimeUnit.MILLISECONDS)
                .observeSafe(AndroidSchedulers.mainThread()) { action() }
                .unsubscribeOnDestroy()
    }

    private fun toggle(opened: Boolean) {
        screenInteractor.publish(sideMode = screenInteractor.state.sideMode.toggle(opened))
    }
}
