package com.noveogroup.template.presentation.palette.all

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.ext.fromHtml
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_palette_all.*

@Layout(R.layout.fragment_palette_all)
class AllFragment : BaseFragment(), AllView {

    @InjectPresenter
    lateinit var presenter: AllPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(AllPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(
                label,
                inputLayout, inputField, validateInputButton,
                check, radio1, radio2,
                ratingBar, seekBar,
                switchButton, toggleButton,
                selectableLabel
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.setOnTouchListener { view, _ ->
            return@setOnTouchListener when (view) {
                inputLayout, inputField -> true
                else -> false.also { deFocus() }
            }
        }

        disableView.setOnCheckedChangeListener { _, checked -> presenter.requestDisable(checked) }

        validateInputButton.setOnClickListener {
            inputLayout.error = when {
                inputField.text.isNullOrEmpty() -> "Can't be empty"
                inputField.text.contains("input", true) -> null
                else -> "Not contains \"input\""
            }
            deFocus()
        }

        selectableLabel.text = """<p>I'm text with link
                                 |<br>
                                 |<a href="">i'm web link</a>
                                 |<br>
                                 |Select me by long tap</p>""".trimMargin().fromHtml()

    }

    override fun enableViews() = changeViewsDisabled(false)

    override fun disableViews() = changeViewsDisabled(true)

    private fun changeViewsDisabled(disabled: Boolean) {
        if (disableView.isChecked != disabled) {
            disableView.isChecked = disabled
        }
        uiControls.forEach { it.isEnabled = disabled }
    }

    private fun deFocus() {
        hideKeyboard()
        focusStealer.requestFocus()
    }

    companion object {
        fun newInstance() = AllFragment()
    }
}
