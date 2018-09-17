package ru.ohmy.theme.presentation.main.page.inheritance

import com.arellomobile.mvp.InjectViewState
import ru.ohmy.theme.R
import ru.ohmy.theme.data.android.system.ResourceManager
import ru.ohmy.theme.domain.interactor.state.ScreenInteractor
import ru.ohmy.theme.domain.interactor.state.model.SideMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.navigation.router.GlobalRouter
import ru.ohmy.theme.presentation.common.mvp.BasePresenter
import javax.inject.Inject

@InjectViewState
class InheritancePresenter @Inject constructor(
        private val resourceManager: ResourceManager,
        private val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<InheritanceView>(globalRouter) {

    override fun attachView(view: InheritanceView?) {
        super.attachView(view)
        screenInteractor.publish(
                sideMode = SideMode.DISABLED,
                toggle = Toggle.BACK,
                title = resourceManager.getString(R.string.title_inheritance)
        )
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.takeIf { shouldShowExplanation }?.showExplanation()
        shouldShowExplanation = false
    }

    companion object {
        var shouldShowExplanation = true
    }
}