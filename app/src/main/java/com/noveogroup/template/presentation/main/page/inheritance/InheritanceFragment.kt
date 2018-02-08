package com.noveogroup.template.presentation.main.page.inheritance

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI

@Layout(R.layout.fragment_inheritance)
class InheritanceFragment : BaseFragment() {

    @InjectPresenter
    lateinit var presenter: InheritancePresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(InheritancePresenter::class.java)!!

    companion object {
        fun newInstance() = InheritanceFragment()
    }
}
