package ru.ohmy.theme.presentation.common.android.menu

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.noveogroup.debugdrawer.data.theme.Theme
import ru.ohmy.theme.presentation.common.android.BaseFragment
import ru.ohmy.theme.presentation.common.mvp.delegate.DrawerDelegate
import ru.ohmy.theme.presentation.common.navigation.BackListener
import kotlinx.android.synthetic.main.fragment_app_menu.*


open class AppMenuFragment : BaseFragment(), AppMenuView, BackListener {

    open lateinit var presenter: AppMenuPresenter

    private var drawerDelegate: DrawerDelegate? = null

    private val drawerListener = DrawerDelegate.composeListener(
            onOpen = { presenter.openMenu() },
            onClose = { presenter.closeMenu() }
    )

    private val themeChangedListener = RadioGroup.OnCheckedChangeListener { _, selectedButtonId -> presenter.changeTheme(selectedButtonId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeChoices.setOnCheckedChangeListener(themeChangedListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerDelegate?.removeListener(drawerListener)
        drawerDelegate = null
    }

    override fun onBackPressed() = (drawerDelegate?.isDrawerOpened ?: false)
            .also { opened -> if (opened) close() }

    fun initialize(drawerDelegate: DrawerDelegate) {
        this.drawerDelegate = drawerDelegate.apply {
            addListener(drawerListener)
            lockClosed()
        }
    }

    override fun open() {
        drawerDelegate?.open()
    }

    override fun close() {
        drawerDelegate?.close()
    }

    override fun lockOpened() {
        drawerDelegate?.lockOpened()
    }

    override fun lockClosed() {
        drawerDelegate?.lockClosed()
    }

    override fun lockAsIs() {
        drawerDelegate?.lockAsIs()
    }

    override fun unlock() {
        drawerDelegate?.unlock()
    }

    override fun selectChoice(theme: Theme) {
        themeChoices.setOnCheckedChangeListener(null)
        when (theme) {
            Theme.BASE_LIGHT -> choiceBaseLight.isChecked = true
            Theme.BASE_DARK -> choiceBaseDark.isChecked = true
            Theme.GREEN_LIGHT -> choiceGreenLight.isChecked = true
            Theme.GREEN_DARK -> choiceGreenDark.isChecked = true
        }
        themeChoices.setOnCheckedChangeListener(themeChangedListener)
    }

}

