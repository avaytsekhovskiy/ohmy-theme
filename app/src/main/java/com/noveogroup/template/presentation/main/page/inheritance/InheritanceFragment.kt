package com.noveogroup.template.presentation.main.page.inheritance

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_inheritance.*

@Layout(R.layout.fragment_inheritance)
class InheritanceFragment : BaseFragment(), InheritanceView {

    @InjectPresenter
    lateinit var presenter: InheritancePresenter

    @ProvidePresenter
    fun providePresenter() = DI.mainScope.getInstance(InheritancePresenter::class.java)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        explainImage.setOnClickListener { showExplanation() }
    }

    override fun showExplanation(): Unit = showSimpleDialog(
            "Example of",
            "Style loses all implicit parent attributes when explicit parent is presented."
    )

    companion object {
        fun newInstance() = InheritanceFragment()
    }
}
