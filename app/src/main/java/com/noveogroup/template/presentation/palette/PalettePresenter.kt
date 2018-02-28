package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.domain.interactor.PaletteInteractor
import com.noveogroup.template.domain.interactor.ThemeInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.palette.page.PaletteTab
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
            PaletteTab.OTHER -> paletteRouter.displayAllControls()
        }
    }

}
