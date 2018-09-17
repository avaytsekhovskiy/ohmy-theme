package ru.ohmy.theme.presentation.palette.part.toolbar

import android.view.Menu
import android.view.MenuItem
import com.jakewharton.rxrelay2.BehaviorRelay
import ru.ohmy.theme.presentation.main.part.toolbar.MenuItemDescriptor
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.combineLatest

class MenuState {

    private val menuView: BehaviorRelay<Menu> = BehaviorRelay.create()
    private val menuContent: BehaviorRelay<Any> = BehaviorRelay.create()

    val allMenuItems: List<MenuItem>
        get() =
            if (menuView.hasValue()) MenuItemDescriptor.values().map { it.asMenuItem(menuView.value) }
            else emptyList()

    fun onMenuReady(view: Menu) = menuView.accept(view)

    fun onContentReady() = menuContent.accept(Any())

    fun observe(observer: (Any) -> Unit): Disposable =
            menuView.toFlowable(BackpressureStrategy.LATEST)
                    .combineLatest(menuContent.toFlowable(BackpressureStrategy.LATEST))
                    .subscribe(observer)

}
