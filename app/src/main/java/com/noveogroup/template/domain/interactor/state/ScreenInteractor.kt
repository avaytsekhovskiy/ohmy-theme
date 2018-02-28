package com.noveogroup.template.domain.interactor.state

import com.jakewharton.rxrelay2.PublishRelay
import com.noveogroup.template.BuildConfig
import com.noveogroup.template.core.ext.debounceWithBuffer
import com.noveogroup.template.core.ext.debugName
import com.noveogroup.template.core.ext.observeSafe
import com.noveogroup.template.core.ext.trackBy
import com.noveogroup.template.domain.common.Publisher
import com.noveogroup.template.domain.interactor.state.model.*
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScreenInteractor @Inject constructor() : Publisher() {

    private val stateChangeRequest = PublishRelay.create<Any>()
    private val stateRelay = PublishRelay.create<ScreenState>()
    private val validator = ScreenValidator()

    var state: ScreenState = initialState
        private set (value) {
            field = value.also { log.info("$debugName new state: $it") }
        }

    init {
        stateChangeRequest.debounceWithBuffer(DEBOUNCE_STATE_TIMEOUT, TimeUnit.MILLISECONDS)
                .map { it.toState() }
                .distinctUntilChanged()
                .doOnNext { validator.check(it) }
                .doOnNext { state = it }
                .subscribe(stateRelay)
                .trackBy(rxHelper)
    }

    fun publish(
            title: String? = null,
            toggle: Toggle? = null,
            toolbarMenu: ToolbarMenu? = null,
            pageMode: PageMode? = null,
            sideMode: SideMode? = null
    ) = arrayOf(pageMode, toggle, title, sideMode, toolbarMenu)
            .filterNotNull()
            .forEach(stateChangeRequest::accept)

    fun publish(state: ScreenState) = with(state) {
        publish(title, toggle, toolbarMenu, pageMode, sideMode)
    }

    fun listenForChanges(scheduler: Scheduler? = null, next: (ScreenState) -> Unit) =
            stateRelay.observeSafe(scheduler = scheduler, consumer = next)
                    .trackBy(rxHelper)

    private fun List<Any>.toState() = validator.autofix(
            title = take(String::class.java) ?: state.title,
            toggle = take(Toggle::class.java) ?: state.toggle,
            pageMode = take(PageMode::class.java) ?: state.pageMode,
            toolbarMenu = take(ToolbarMenu::class.java) ?: state.toolbarMenu,
            sideMode = take(SideMode::class.java) ?: state.sideMode
    )

    private fun <T : Any> List<Any>.take(type: Class<T>): T? =
            find(type::isInstance)?.let(type::cast)

    private companion object {
        //usually BasePresenter calls publish methods N times in a row. And it takes 5 milliseconds
        const val DEBOUNCE_STATE_TIMEOUT = 5L //ms

        val initialState = ScreenState(
                title = "\$\$ Default Title \$\$".takeIf { BuildConfig.DEBUG } ?: "",
                toggle = Toggle.HIDDEN,
                toolbarMenu = ToolbarMenu.NO_MENU,
                pageMode = PageMode.TOOLBAR,
                sideMode = SideMode.CLOSED)
    }

}
