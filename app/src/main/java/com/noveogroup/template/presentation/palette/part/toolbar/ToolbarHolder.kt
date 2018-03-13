package com.noveogroup.template.presentation.palette.part.toolbar

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
import com.noveogroup.template.R
import com.noveogroup.template.core.rx.RxHelper
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.BaseMvpComponent
import com.noveogroup.template.presentation.common.ext.ButterKnife
import com.noveogroup.template.presentation.common.ext.ViewBinder
import com.noveogroup.template.presentation.common.ext.bindView
import com.noveogroup.template.presentation.common.ext.colorizeDrawable
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.main.part.toolbar.MenuItemDescriptor

class ToolbarHolder(activity: BaseActivity) : BaseMvpComponent(activity), ToolbarView, ViewBinder {

    override val container: View = activity.findViewById(android.R.id.content)

    private val rxHelper = RxHelper()
    private val state = MenuState()

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val actionBar get() = activity.supportActionBar

    @InjectPresenter
    lateinit var toolbarPresenter: ToolbarPresenter

    @ProvidePresenter
    fun providePresenter(): ToolbarPresenter = DI.paletteScope.getInstance(ToolbarPresenter::class.java)

    override fun onCreate() {
        super.onCreate()
        activity.setSupportActionBar(toolbar)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener { toolbarPresenter.back() }

        rxHelper.add(state.observe { state.allMenuItems.forEach { it.isVisible = true } })
        state.onContentReady()

        toolbar.isVisible = true
    }

    override fun changeTitle(title: String) {
        log.debug("Title changed $title")
        actionBar?.title = title //automatically changes title of Toolbar container.
    }

    @SuppressLint("RestrictedApi")
    fun setMenu(menu: Menu) {
        state.onMenuReady(menu)

        //hack to make overflow icons visible
        (menu as? MenuBuilder)?.setOptionalIconsVisible(true)

        //colorize toolbar icons from theme
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

    fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val descriptor = menuItem.asDescriptor() ?: return false
        toolbarPresenter.handleMenuItemClick(descriptor)
        return true
    }

    fun onDestroy() {
        ButterKnife.reset(this)
        rxHelper.unsubscribeAll()
    }

    private fun MenuItem.asDescriptor(): MenuItemDescriptor? =
            MenuItemDescriptor.findById(itemId)

}
