package com.noveogroup.template.presentation.main.part.toolbar

import android.annotation.SuppressLint
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuItemImpl
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.core.ext.warnOrThrow
import com.noveogroup.template.core.rx.RxHelper
import com.noveogroup.template.domain.interactor.state.model.PageMode
import com.noveogroup.template.domain.interactor.state.model.Toggle
import com.noveogroup.template.domain.interactor.state.model.ToolbarMenu
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.BaseMvpComponent
import com.noveogroup.template.presentation.common.ext.*
import com.noveogroup.template.presentation.common.mvp.delegate.DrawerDelegate
import com.noveogroup.template.presentation.di.DI

class ToolbarHolder(
        activity: BaseActivity,
        private val drawerHelper: DrawerDelegate
) : BaseMvpComponent(activity), ToolbarView, ViewBinder {

    override val container: View = activity.findViewById(android.R.id.content)

    private val rxHelper = RxHelper()
    private val state = MenuState()

    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private lateinit var toggle: ActionBarDrawerToggle
    private val actionBar get() = activity.supportActionBar

    @InjectPresenter
    lateinit var toolbarPresenter: ToolbarPresenter

    @ProvidePresenter
    fun providePresenter(): ToolbarPresenter = DI.mainScope.getInstance(ToolbarPresenter::class.java)

    override fun onCreate() {
        super.onCreate()
        activity.setSupportActionBar(toolbar)

        toggle = drawerHelper.createToggle(activity, toolbar).also(drawerHelper::addListener)
        toolbar.setNavigationOnClickListener { toolbarPresenter.handleActionBarToggle() }

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
    override fun changeToggle(icon: Toggle) {
        log.debug("Toggle changed $icon")
        when (icon) {
            Toggle.BURGER -> {
                actionBar?.setDisplayHomeAsUpEnabled(false)
                toggle.isDrawerIndicatorEnabled = true
                toggle.syncState()
            }
            Toggle.BACK -> actionBar?.setDisplayHomeAsUpEnabled(true)
            else -> log.warnOrThrow("Unknown toggle icon")
        }
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    override fun changePageMode(pageMode: PageMode) {
        log.debug("PageMode changed $pageMode")
        when (pageMode) {
            PageMode.TOOLBAR -> toolbar.show()
            PageMode.FULLSCREEN_MODAL -> toolbar.hide()
            else -> log.warnOrThrow("unknown PageMode")
        }
    }

    fun onOptionsItemSelected(menuItem: MenuItem) = menuItem.asDescriptor()
            ?.let { toolbarPresenter.handleMenuItemClick(it) }
            ?.let { true } ?: false

    @SuppressLint("RestrictedApi")
    fun setMenu(menu: Menu) {
        state.onMenuReady(menu)

        //hack to make overflow icons visible
        (menu as? MenuBuilder)?.setOptionalIconsVisible(true)

        //colorize overflow icons programmatically to primary dark
        state.allMenuItems.forEach {
            if (it is MenuItemImpl && !it.requiresActionButton()) {
                it.icon = toolbar.context.colorizeDrawable(it.icon, R.attr.colorPrimaryDark)
            }
        }

        toolbarPresenter.requestAppearanceRefresh()
    }

    fun onDestroy() {
        drawerHelper.removeListener(toggle)
        ButterKnife.reset(this)
        rxHelper.unsubscribeAll()
    }

    private fun MenuItem.asDescriptor(): MenuItemDescriptor? =
            MenuItemDescriptor.findById(itemId)
}
