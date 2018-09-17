package ru.ohmy.theme.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.domain.navigation.router.MainRouter
import ru.ohmy.theme.presentation.common.android.BaseActivity
import ru.ohmy.theme.presentation.common.android.BaseFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.common.ext.findFragmentByContainer
import ru.ohmy.theme.presentation.common.mvp.delegate.DrawerDelegate
import ru.ohmy.theme.presentation.common.navigation.NavigatorProvider
import ru.ohmy.theme.presentation.di.ActivityScopeInitializer
import ru.ohmy.theme.presentation.di.DI
import ru.ohmy.theme.presentation.main.part.menu.MainMenuFragment
import ru.ohmy.theme.presentation.main.part.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity(), MainView, NavigatorProvider {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(MainPresenter::class.java)!!

    override val lazyScope by lazy { ActivityScopeInitializer { DI.mainScope } }

    @Inject
    lateinit var mainRouter: MainRouter

    private lateinit var toolbarHolder: ToolbarHolder

    private val menuFragment: MainMenuFragment
        get() = findFragmentByContainer(menuContainer)!!

    private val currentMainFragment: BaseFragment
        get() = findFragmentByContainer(mainContainer)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            MainMenuFragment().addTo(menuContainer)
        }

        toolbarHolder = ToolbarHolder(this).apply { onCreate() }
        DrawerDelegate(DrawerDelegate.Orientation.RIGHT, menuContainer, drawer).let { drawer ->
            menuFragment.initialize(drawer)
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
        menuInflater.inflate(R.menu.toolbar, menu)
        toolbarHolder.setMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            toolbarHolder.onOptionsItemSelected(item)

    override fun onBackPressed() {
        if (processBackIfListener(menuFragment)) return
        if (processBackIfListener(currentMainFragment)) return
        presenter.back()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
