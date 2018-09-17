package ru.ohmy.theme.presentation.palette.page.buttons

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.R
import ru.ohmy.theme.core.ext.observeSafe
import ru.ohmy.theme.data.android.system.ResourceManager
import ru.ohmy.theme.domain.interactor.PaletteInteractor
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ButtonsPresenter @Inject constructor(
        private val paletteInteractor: PaletteInteractor,
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ButtonsView>(globalRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        paletteInteractor.observeExplain()
                .observeSafe(AndroidSchedulers.mainThread()) {
                    viewState.showExplanation()
                }
                .unsubscribeOnDestroy()

        paletteInteractor.observeEnable()
                .observeSafe(AndroidSchedulers.mainThread()) { enabled ->
                    if (enabled) viewState.enableViews()
                    else viewState.disableViews()
                }
                .unsubscribeOnDestroy()
    }

    override fun attachView(view: ButtonsView?) {
        super.attachView(view)
        screenInteractor.publish(
                title = resourceManager.getString(R.string.palette_title_buttons)
        )
    }

    fun requestDialog(light: Boolean) = with(viewState) {
        if (light) showLightDialog()
        else showDarkDialog()
    }

}