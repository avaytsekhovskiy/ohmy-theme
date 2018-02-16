package com.noveogroup.template.presentation.palette

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.debugdrawer.data.theme.Theme
import com.noveogroup.debugdrawer.data.theme.ThemeProxy
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.helper.orientation.ActivityOrientation
import com.noveogroup.template.presentation.common.android.helper.orientation.OrientationHelper
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.navigation.NavigatorProvider
import com.noveogroup.template.presentation.di.ActivityScopeInitializer
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.palette.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_palette.*
import javax.inject.Inject

@Layout(R.layout.activity_palette)
class PaletteActivity : BaseActivity(), NavigatorProvider, PaletteView {

    @Inject
    lateinit var paletteRouter: PaletteRouter

    @Inject
    lateinit var themeProxy: ThemeProxy

    @Inject
    lateinit var resourceManager: ResourceManager

    @InjectPresenter
    internal lateinit var presenter: PalettePresenter

    @ProvidePresenter
    fun providePresenter() =
            DI.paletteScope.getInstance(PalettePresenter::class.java)!!

    override val orientationHelper by lazy { OrientationHelper(this, ActivityOrientation.BOTH, ActivityOrientation.BOTH) }

    override val lazyScope by lazy { ActivityScopeInitializer { DI.paletteScope } }
    override val themeId by lazy {
        themeProxy.read().let {
            when (it) {
                Theme.BASE_LIGHT -> R.style.AppTheme_Light
                Theme.BASE_DARK -> R.style.AppTheme_Dark
                Theme.GREEN_LIGHT -> R.style.AppTheme_Light_Green
                Theme.GREEN_DARK -> R.style.AppTheme_Dark_Green
            }
        }.also { log.warn("themeId = $it") }
    }

    private lateinit var toolbarHolder: ToolbarHolder

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarHolder = ToolbarHolder(this).apply { onCreate() }

        addButton(PaletteTab.HOME)
        addButton(PaletteTab.BUTTONS)
        addButton(PaletteTab.OTHER)

        bottomTabs.setOnNavigationItemSelectedListener {
            presenter.openPage(PaletteTab.values()[it.itemId])
            return@setOnNavigationItemSelectedListener true
        }

        explainButton.setOnClickListener { presenter.explain() }
        disableView.setOnCheckedChangeListener { _, checked -> presenter.disable(checked) }
    }

    override fun onInstallNavigator() {
        paletteRouter.setNavigator(PaletteNavigator(this, R.id.pageContainer))
    }

    override fun onReleaseNavigator() {
        paletteRouter.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbarHolder.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        toolbarHolder.setMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = toolbarHolder.onOptionsItemSelected(item)

    override fun onBackPressed() = presenter.back()

    override fun showSettings() = debugDrawer.openDrawer()

    override fun selectTab(position: PaletteTab) {
        bottomTabs.selectedItemId = position.ordinal
    }

    private fun addButton(tab: PaletteTab): MenuItem {
        return bottomTabs.menu.add(Menu.NONE, tab.ordinal, Menu.NONE, getString(tab.stringRes)).apply {
            icon = resourceManager.getDrawable(tab.drawableRes)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PaletteActivity::class.java)
    }

}
