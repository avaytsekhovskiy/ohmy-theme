package com.noveogroup.template.presentation.main.part.toolbar

import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxrelay2.BehaviorRelay
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.combineLatest

class MenuState {

    class MenuStateDescriptor(
            private val menuView: Menu,
            private val menuContent: ToolbarMenu
    ) {
        val allowed
            get() = ALLOWED_MAP[menuContent]
                    ?.map { it.asMenuItem(menuView) } ?: emptyList()
    }

    private val menuView: BehaviorRelay<Menu> = BehaviorRelay.create<Menu>()
    private val menuContent: BehaviorRelay<ToolbarMenu> = BehaviorRelay.create<ToolbarMenu>()

    val allMenuItems: List<MenuItem>
        get() =
            if (menuView.hasValue()) MenuItemDescriptor.values().map { it.asMenuItem(menuView.value) }
            else emptyList()

    fun onMenuReady(view: Menu) = menuView.accept(view)

    fun onContentReady(content: ToolbarMenu) = menuContent.accept(content)

    fun observe(observer: (MenuStateDescriptor) -> Unit): Disposable =
            menuView.toFlowable(BackpressureStrategy.LATEST)
                    .combineLatest(menuContent.toFlowable(BackpressureStrategy.LATEST))
                    .map { MenuStateDescriptor(it.first, it.second) }
                    .subscribe(observer)

    companion object {
        val ALLOWED_MAP = mapOf(
                ToolbarMenu.MENU to listOf(
                        MenuItemDescriptor.SAVE,
                        MenuItemDescriptor.SHARE_MAIL, MenuItemDescriptor.SHARE_SMS
                ),
                ToolbarMenu.NO_MENU to emptyList())
    }

}
