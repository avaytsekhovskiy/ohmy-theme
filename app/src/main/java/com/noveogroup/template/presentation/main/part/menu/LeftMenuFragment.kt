package com.noveogroup.template.presentation.main.part.menu

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.mvp.delegate.DrawerDelegate
import com.noveogroup.template.presentation.common.navigation.BackListener
import com.noveogroup.template.presentation.di.DI


@Layout(R.layout.fragment_menu_left)
class LeftMenuFragment : BaseFragment(), LeftMenuView, BackListener {

    @InjectPresenter
    lateinit var presenter: LeftMenuPresenter

    @ProvidePresenter
    fun providePresenter(): LeftMenuPresenter = DI.mainScope.getInstance(LeftMenuPresenter::class.java)

    private var drawerDelegate: DrawerDelegate? = null

    private val drawerListener = DrawerDelegate.composeListener(
            onOpen = { presenter.openMenu() },
            onClose = { presenter.closeMenu() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener { menuView -> presenter.openSomething(menuView.javaClass.simpleName) }
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

}

