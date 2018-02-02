package com.noveogroup.template.presentation.main.page.welcome

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_welcome.*

@Layout(R.layout.fragment_welcome)
class WelcomeFragment : BaseFragment(), WelcomeView {

    @InjectPresenter
    lateinit var presenter: WelcomePresenter

    @ProvidePresenter
    fun providePresenter(): WelcomePresenter = DI.mainScope.getInstance(WelcomePresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextButton.setOnClickListener { presenter.openNext() }
    }

    override fun showName(name: String) {
        nameView.text = name
    }

    companion object {
        fun newInstance() = WelcomeFragment()
    }
}
