package ru.ohmy.theme.presentation.palette

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.domain.interactor.PaletteInteractor
import ru.ohmy.theme.domain.interactor.ThemeInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.model.PageMode
import ru.ohmy.theme.domain.interactor.state.model.SideMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.domain.navigation.router.PaletteRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import ru.ohmy.theme.presentation.palette.page.PaletteTab
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class PalettePresenter @Inject constructor(
        private val themeInteractor: ThemeInteractor,
        private val paletteInteractor: PaletteInteractor,
        private val paletteRouter: PaletteRouter,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<PaletteView>(globalRouter) {

    private var position: PaletteTab = PaletteTab.values()[0]

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        themeInteractor.observeSettings()
                .observeSafe(AndroidSchedulers.mainThread()) { viewState.recreate() }
                .unsubscribeOnDestroy()

        openPageWithoutChecks(position)
    }

    override fun attachView(view: PaletteView?) {
        super.attachView(view)
        screenInteractor.publish(
                pageMode = PageMode.TOOLBAR,
                toggle = Toggle.BACK,
                sideMode = SideMode.CLOSED
        )

        viewState.selectTab(position)
    }

    override fun back() = globalRouter.exit()

    fun openPage(tab: PaletteTab) {
        if (position == tab) return

        openPageWithoutChecks(tab)
        viewState.selectTab(tab)
    }

    fun explain() = paletteInteractor.explain()

    fun enable(enabled: Boolean) = paletteInteractor.enable(enabled)

    private fun openPageWithoutChecks(tab: PaletteTab) {
        position = tab
        when (tab) {
            PaletteTab.SELECTORS -> paletteRouter.displaySelectors()
            PaletteTab.BUTTONS -> paletteRouter.displayButtons()
            PaletteTab.PICKERS -> paletteRouter.displayPickers()
            PaletteTab.TEXTS -> paletteRouter.displayAllControls()
        }
    }

}
