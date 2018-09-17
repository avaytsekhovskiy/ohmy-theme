package ru.ohmy.theme.presentation.main.page.inheritance

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.BaseFragment
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.DI
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
