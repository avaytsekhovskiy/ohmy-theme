package com.noveogroup.template.presentation.main.part.toolbar

import android.annotation.SuppressLint
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
import com.noveogroup.template.presentation.di.DI

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
            PageMode.TOOLBAR -> toolbar.show()
            PageMode.FULLSCREEN_MODAL -> toolbar.hide()
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
