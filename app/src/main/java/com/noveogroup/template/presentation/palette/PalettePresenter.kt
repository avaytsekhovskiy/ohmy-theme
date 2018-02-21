package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import com.noveogroup.preferences.guava.Optional
import com.noveogroup.preferences.lambda.Consumer
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.interactor.PaletteInteractor
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.SideMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class PalettePresenter @Inject constructor(
        private val themeProxy: ThemeProxy,
        private val paletteInteractor: PaletteInteractor,
        private val paletteRouter: PaletteRouter,
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<PaletteView>(globalRouter) {

    private val themeChangedListener = Consumer<Optional<Int>> {
        viewState.hideSettings()
        viewState.recreate()
    }

    private var position: PaletteTab = PaletteTab.OTHER

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paletteInteractor.observeSettings()
                .observeSafe(AndroidSchedulers.mainThread()) { viewState.showSettings() }
                .unsubscribeOnDestroy()

        themeProxy.addListener(themeChangedListener)
        openPageWithoutChecks(position)
    }

    override fun attachView(view: PaletteView?) {
        super.attachView(view)
        screenInteractor.publish(
                toggle = Toggle.BACK,
                pageMode = PageMode.TOOLBAR,
                sideMode = SideMode.DISABLED
        )

        viewState.selectTab(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        themeProxy.removeListener(themeChangedListener)
    }

    override fun back() = globalRouter.exit()

    fun openPage(tab: PaletteTab) {
        if (position == tab) return

        openPageWithoutChecks(tab)
        viewState.selectTab(tab)
    }

    fun explain() = paletteInteractor.explain()

    fun disable(disabled: Boolean) = paletteInteractor.disable(disabled)

    private fun openPageWithoutChecks(tab: PaletteTab) {
        position = tab
        when (tab) {
            PaletteTab.SELECTORS -> paletteRouter.displaySelectors()
            PaletteTab.BUTTONS -> paletteRouter.displayButtons()
            PaletteTab.OTHER -> paletteRouter.displayAllControls()
            PaletteTab.OVERVIEW -> paletteRouter.displayOverview()
        }
    }

}
