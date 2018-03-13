package com.noveogroup.template.presentation.palette.page.buttons

import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AlertDialog
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.PagerFragment
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import kotlinx.android.synthetic.main.fragment_buttons.*

@Layout(R.layout.fragment_buttons)
class ButtonsFragment : PagerFragment(), ButtonsView {

    @InjectPresenter
    lateinit var presenter: ButtonsPresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(ButtonsPresenter::class.java)!!

    private val uiControls by lazy {
        listOf(normalButton, normalFlatButton,
                coloredButton, coloredFlatButton,
                toggleButton1, toggleButton2, switchButton)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOf(normalButton, normalFlatButton).forEach {
            it.setOnClickListener { presenter.requestDialog(true) }
        }
        listOf(coloredButton, coloredFlatButton).forEach {
            it.setOnClickListener { presenter.requestDialog(false) }
        }
    }

    override fun enableViews() = changeViewsEnabled(true)

    override fun disableViews() = changeViewsEnabled(false)

    private fun changeViewsEnabled(enabled: Boolean) = uiControls.forEach { it.isEnabled = enabled }

    override fun showLightDialog() = showStyledDialog(R.style.AppThemeOverlay_Dialog_Alert_Light)

    override fun showDarkDialog() = showStyledDialog(R.style.AppThemeOverlay_Dialog_Alert_Dark)

    private fun showStyledDialog(@StyleRes dialogTheme: Int) = dialogDelegate.showDialog(
            AlertDialog.Builder(context!!, dialogTheme).run {
                setTitle("Title")
                setMessage("Lots of letters")
                setPositiveButton("OK", { _, _ -> cancelDialog() })
                setNegativeButton("NOK", { _, _ -> cancelDialog() })
                setNeutralButton("Dunno", { _, _ -> cancelDialog() })
                return@run create()
            }
    )

    override fun showExplanation() {
        dialogDelegate.showSimpleDialog(
                title = "Button Styles",
                description = """
                               |AppTheme (ohmyCompat)
                               |  normal = ?secondary
                               |  disabled = @color/grey
                               |
                               |AppTheme.Button
                               |  textColor = ?secondary
                               |  background = ?normal/?disabled
                               |
                               |AppTheme.Button.Colored
                               |  textColor = ?secondaryInverse
                               |  background = ?accent/?disabled
                               |
                               |AppTheme.Button.Borderless
                               |  textColor = ?secondary
                               |  background = @null/?disabled
                               |
                               |AppTheme.Button.Borderless.Colored
                               |  textColor = ?accent
                               |  background = @null/?disabled
                                """.trimMargin()
        )
    }

    companion object {
        fun newInstance() = ButtonsFragment()
    }
}
