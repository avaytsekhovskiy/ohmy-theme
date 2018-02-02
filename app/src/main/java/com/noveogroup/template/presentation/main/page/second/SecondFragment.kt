package com.noveogroup.template.presentation.main.page.second

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_second.*

@Layout(R.layout.fragment_second)
class SecondFragment : BaseFragment() {

    @InjectPresenter
    lateinit var presenter: SecondPresenter

    @ProvidePresenter
    fun providePresenter(): SecondPresenter = DI.mainScope.getInstance(SecondPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popButton.setOnClickListener { presenter.back() }
    }

    companion object {
        fun newInstance() = SecondFragment()
    }
}
