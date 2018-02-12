package com.noveogroup.template.presentation.palette.toolbar

import com.arellomobile.mvp.InjectViewState
import com.noveogroup.template.domain.interactor.state.ScreenInteractor
import com.noveogroup.template.domain.interactor.state.ScreenStateDiffHelper
import com.noveogroup.template.domain.interactor.state.model.ScreenState
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.mvp.BasePresenter
import com.noveogroup.template.presentation.common.mvp.ScreenStateListener
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ToolbarPresenter @Inject constructor(
        override val screenInteractor: ScreenInteractor,
        globalRouter: GlobalRouter
) : BasePresenter<ToolbarView>(globalRouter), ScreenStateListener {

    override var previousState: ScreenState? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        screenInteractor
                .listenForChanges(AndroidSchedulers.mainThread(), ::dispatchAppearance)
                .unsubscribeOnDestroy()
    }

    override fun onScreenStateChanged(helper: ScreenStateDiffHelper) = with(helper) {
        ifTitleChanged(viewState::changeTitle)
    }

    override fun back() = globalRouter.exit()

}
