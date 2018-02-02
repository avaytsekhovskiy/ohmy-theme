package com.noveogroup.template.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.domain.navigation.router.MainRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.ext.findFragmentByContainer
import com.noveogroup.template.presentation.common.mvp.delegate.DrawerDelegate
import com.noveogroup.template.presentation.common.navigation.NavigatorProvider
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.main.part.menu.LeftMenuFragment
import com.noveogroup.template.presentation.main.part.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity(), MainView, NavigatorProvider {

    override val scopeInitializer = MainScopeInitializer(this)

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = DI.mainScope.getInstance(MainPresenter::class.java)

    @Inject
    lateinit var mainRouter: MainRouter

    private lateinit var toolbarHolder: ToolbarHolder

    private val leftMenuFragment: LeftMenuFragment
        get() = findFragmentByContainer(leftContainer)!!

    private val currentMainFragment: BaseFragment
        get() = findFragmentByContainer(mainContainer)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LeftMenuFragment().addTo(leftContainer)
        }

        DrawerDelegate(DrawerDelegate.Orientation.LEFT, leftContainer, drawer).let { drawer ->
            leftMenuFragment.initialize(drawer)
            toolbarHolder = ToolbarHolder(this, drawer).apply { onCreate() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbarHolder.onDestroy()
    }

    override fun onInstallNavigator() =
            mainRouter.setNavigator(MainNavigator(this, mainContainer.id))

    override fun onReleaseNavigator() =
            mainRouter.removeNavigator()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        toolbarHolder.setMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            toolbarHolder.onOptionsItemSelected(item)

    override fun onBackPressed() {
        if (processBackIfListener(leftMenuFragment)) return
        if (processBackIfListener(currentMainFragment)) return
        presenter.back()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
