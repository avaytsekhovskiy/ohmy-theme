package com.noveogroup.template.presentation.palette

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.data.android.system.ResourceManager
import com.noveogroup.template.domain.navigation.router.PaletteRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.helper.orientation.ActivityOrientation
import com.noveogroup.template.presentation.common.android.helper.orientation.OrientationHelper
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.ext.findFragmentByContainer
import com.noveogroup.template.presentation.common.mvp.delegate.DrawerDelegate
import com.noveogroup.template.presentation.common.navigation.NavigatorProvider
import com.noveogroup.template.presentation.di.ActivityScopeInitializer
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.palette.page.PaletteTab
import com.noveogroup.template.presentation.palette.part.menu.PaletteMenuFragment
import com.noveogroup.template.presentation.palette.part.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_palette.*
import javax.inject.Inject


@Layout(R.layout.activity_palette)
class PaletteActivity : BaseActivity(), PaletteView, NavigatorProvider {

    @Inject
    lateinit var paletteRouter: PaletteRouter

    @Inject
    lateinit var resourceManager: ResourceManager

    @InjectPresenter
    internal lateinit var presenter: PalettePresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(PalettePresenter::class.java)!!

    override val orientationHelper by lazy { OrientationHelper(this, ActivityOrientation.BOTH, ActivityOrientation.BOTH) }

    override val lazyScope by lazy { ActivityScopeInitializer { DI.paletteScope } }

    private lateinit var toolbarHolder: ToolbarHolder

    private val menuFragment: PaletteMenuFragment
        get() = findFragmentByContainer(menuContainer)!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            PaletteMenuFragment().addTo(menuContainer)
        }

        toolbarHolder = ToolbarHolder(this).apply { onCreate() }
        DrawerDelegate(DrawerDelegate.Orientation.RIGHT, menuContainer, drawer).let { drawer ->
            menuFragment.initialize(drawer)
        }

        PaletteTab.values().forEach { addButton(it) }

        bottomTabs.setOnNavigationItemSelectedListener {
            log.debug("tab selected listener $it")
            presenter.openPage(PaletteTab.values()[it.itemId])
            return@setOnNavigationItemSelectedListener true
        }
        bottomTabs.disableShiftMode()

        explainButton.setOnClickListener { presenter.explain() }
        enableView.setOnCheckedChangeListener { _, checked -> presenter.enable(checked) }
    }

    override fun onInstallNavigator() {
        paletteRouter.setNavigator(PaletteNavigator(this, R.id.pageContainer))
    }

    override fun onReleaseNavigator() {
        paletteRouter.removeNavigator()
    }

    @SuppressLint("RestrictedApi")
    private fun BottomNavigationView.disableShiftMode() = try {
        (getChildAt(0) as BottomNavigationMenuView).let {
            it.javaClass.getDeclaredField("mShiftingMode").run {
                isAccessible = true
                setBoolean(it, false)
                isAccessible = false
            }
            for (i in 0 until it.childCount) {
                (it.getChildAt(i) as BottomNavigationItemView).run {
                    setShiftingMode(false)
                    setChecked(itemData.isChecked)
                }
            }
        }
    } catch (e: NoSuchFieldException) {
    } catch (e: IllegalAccessException) {
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

    override fun onBackPressed() {
        if (processBackIfListener(menuFragment)) return
        presenter.back()
    }

    override fun selectTab(position: PaletteTab) {
        log.debug("select tab command $position")
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
