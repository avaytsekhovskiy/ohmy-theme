package com.noveogroup.template.presentation.palette.page.texts

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.PagerFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.ext.fromHtml
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_texts.*

@Layout(R.layout.fragment_texts)
class TextsFragment : PagerFragment(), TextsView {

    @InjectPresenter
    lateinit var presenter: TextsPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(TextsPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(
                selectableLabel,
                inputLayout, inputField, validateInputButton
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.setOnTouchListener { touchedView, _ ->
            return@setOnTouchListener when (touchedView) {
                inputLayout, inputField -> true
                else -> false.also { deFocus() }
            }
        }

        validateInputButton.setOnClickListener {
            inputLayout.error = when {
                inputField.text.isNullOrEmpty() -> "Can't be empty"
                inputField.text.contains("input", true) -> null
                else -> "Not contains \"input\""
            }
            deFocus()
        }

        selectableLabel.movementMethod = LinkMovementMethod.getInstance()
        selectableLabel.text = """<p>I'm text with link
                                 |<br>
                                 |<a href="https://github.com/avaytsekhovskiy/ohmy-theme">i'm web link</a>
                                 |<br>
                                 |Select me by long tap</p>
                                 |<p>The form input is quite tricky.
                                 |It is valid only if contains "input" word.
                                 |You can validate it after input - and can see behavior for error and hint labels.
                                 |Its styled with colors.
                                 |<br>
                                 |Tip: tap outside to remove focus from EditText box.
                                 |</p>""".trimMargin().fromHtml()

    }

    override fun enableViews() = changeViewsDisabled(true)

    override fun disableViews() = changeViewsDisabled(false)

    private fun changeViewsDisabled(enabled: Boolean) {
        uiControls.forEach { it.isEnabled = enabled }
    }

    override fun showExplanation() {
        dialogDelegate.showSimpleDialog(
                title = "Theme attrs",
                description = """
                               |just all other views
                               |
                               |Soon will be splitted
                                """.trimMargin()
        )
    }

    private fun deFocus() {
        hideKeyboard()
        focusStealer.requestFocus()
    }

    companion object {
        fun newInstance() = TextsFragment()
    }
}
