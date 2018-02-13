package com.noveogroup.template.presentation.palette

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StyleRes
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
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

    @InjectPresenter
    internal lateinit var presenter: PalettePresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(PalettePresenter::class.java)!!

    override val orientationHelper by lazy { OrientationHelper(this, ActivityOrientation.BOTH, ActivityOrientation.BOTH) }

    override val lazyScope by lazy { ActivityScopeInitializer { DI.paletteScope } }
    override val themeId by lazy { intent.getIntExtra(EXTRA_THEME, 0) }

    private lateinit var toolbarHolder: ToolbarHolder

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarHolder = ToolbarHolder(this).apply { onCreate() }

        switchExampleButton.setOnClickListener { presenter.replaceExample() }
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

    companion object {
        private const val EXTRA_THEME = "EXTRA_THEME_BOOLEAN_IS_LIGHT"

        fun newIntent(context: Context, @StyleRes styleRes: Int) = Intent(context, PaletteActivity::class.java).apply {
            putExtra(EXTRA_THEME, styleRes)
        }
    }

}
