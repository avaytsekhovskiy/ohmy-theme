package ru.ohmy.theme.presentation.main.part.toolbar

import android.annotation.SuppressLint
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.core.ext.warnOrThrow
import ru.ohmy.theme.core.rx.RxHelper
import ru.ohmy.theme.domain.interactor.state.model.PageMode
import ru.ohmy.theme.domain.interactor.state.model.Toggle
import ru.ohmy.theme.domain.interactor.state.model.ToolbarMenu
import ru.ohmy.theme.presentation.common.android.BaseActivity
import ru.ohmy.theme.presentation.common.android.BaseMvpComponent
import ru.ohmy.theme.presentation.common.ext.ButterKnife
import ru.ohmy.theme.presentation.common.ext.ViewBinder
import ru.ohmy.theme.presentation.common.ext.bindView
import ru.ohmy.theme.presentation.common.ext.colorizeDrawable
import ru.ohmy.theme.presentation.di.DI

class ToolbarHolder(
        activity: BaseActivity
) : BaseMvpComponent(activity), ToolbarView, ViewBinder {

    override val container: View = activity.findViewById(android.R.id.content)

    private val rxHelper = RxHelper()
    private val state = MenuState()

    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private val actionBar get() = activity.supportActionBar

    @InjectPresenter
    lateinit var toolbarPresenter: ToolbarPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(ToolbarPresenter::class.java)!!

    override fun onCreate() {
        super.onCreate()
        activity.setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { toolbarPresenter.back() }

        rxHelper.add(state.observe { descriptor ->
            val allowed = descriptor.allowed
            state.allMenuItems.forEach { menuItem ->
                menuItem.isVisible = allowed.contains(menuItem)
            }
        })
    }

    override fun changeTitle(title: String) {
        log.debug("Title changed $title")
        actionBar?.title = title //automatically changes title of Toolbar container.
    }

    override fun changeToolbarMenu(toolbarMenu: ToolbarMenu) {
        log.debug("ToolbarMenu changed $toolbarMenu")
        state.onContentReady(toolbarMenu)
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    override fun changePageMode(pageMode: PageMode) {
        log.debug("PageMode changed $pageMode")
        when (pageMode) {
            PageMode.TOOLBAR -> toolbar.isVisible = true
            PageMode.FULLSCREEN_MODAL -> toolbar.isVisible = false
            else -> log.warnOrThrow("unknown PageMode")
        }
    }

    override fun changeToggle(toggle: Toggle) {
        log.debug("Toggle changed $toggle")
        when (toggle) {
            Toggle.HIDDEN -> actionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
            }
            Toggle.BACK -> actionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
            }
            else -> log.warnOrThrow("unknown PageMode")
        }
    }

    fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val descriptor = menuItem.asDescriptor() ?: return false
        toolbarPresenter.handleMenuItemClick(descriptor)
        return true
    }

    @SuppressLint("RestrictedApi")
    fun setMenu(menu: Menu) {
        state.onMenuReady(menu)

        //hack to make overflow icons visible
        (menu as? MenuBuilder)?.setOptionalIconsVisible(true)

        //colorize overflow icons programmatically to primary dark
        state.allMenuItems.forEach {
            if (it is MenuItemImpl) {
                val attrId = when {
                    it.requiresActionButton() -> android.R.attr.textColorPrimary
                    else -> R.attr.colorPrimaryDark
                }
                it.icon = toolbar.context.colorizeDrawable(it.icon, attrId)
            }
        }

        toolbarPresenter.requestAppearanceRefresh()
    }

    fun onDestroy() {
        ButterKnife.reset(this)
        rxHelper.unsubscribeAll()
    }

    private fun MenuItem.asDescriptor(): MenuItemDescriptor? =
            MenuItemDescriptor.findById(itemId)
}
