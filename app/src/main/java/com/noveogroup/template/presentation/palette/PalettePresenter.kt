package com.noveogroup.template.presentation.palette

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import com.noveogroup.preferences.guava.Optional
import com.noveogroup.preferences.lambda.Consumer
import com.noveogroup.template.R
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

    private var position = 0

    private val consumer = Consumer<Optional<Int>> { globalRouter.replacePalette() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paletteInteractor.observeSettings()
                .observeSafe(AndroidSchedulers.mainThread()) { viewState.showSettings() }
                .unsubscribeOnDestroy()

        replaceExample()
        themeProxy.addListener(consumer)
    }

    override fun attachView(view: PaletteView?) {
        super.attachView(view)
        screenInteractor.publish(
                title = resourceManager.getString(R.string.palette_title),
                toggle = Toggle.BACK,
                pageMode = PageMode.TOOLBAR,
                sideMode = SideMode.DISABLED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        themeProxy.removeListener(consumer)
    }

    override fun back() = globalRouter.exit()

    fun replaceExample() {
        when (position) {
            0 -> paletteRouter.displayButtons()
            else -> paletteRouter.displayAllControls()
        }

        position = (position + 1) % 2
    }

    fun explain() {
        paletteInteractor.explain()
    }

    fun disable(disabled: Boolean) {
        paletteInteractor.disable(disabled)
    }

}
