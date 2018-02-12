package com.noveogroup.template.presentation.palette

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.ActivityScopeInitializer
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.palette.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_palette.*

@Layout(R.layout.activity_palette)
class PaletteActivity : BaseActivity(), PaletteView {

    @InjectPresenter
    internal lateinit var presenter: PalettePresenter

    @ProvidePresenter
    fun providePresenter() = DI.paletteScope.getInstance(PalettePresenter::class.java)!!

    override val lazyScope by lazy { ActivityScopeInitializer { DI.paletteScope } }
    override val themeId by lazy { intent.getIntExtra(EXTRA_THEME, 0) }

    private lateinit var toolbarHolder: ToolbarHolder

    private val uiControls: List<View> by lazy {
        return@lazy listOf(
                label, inputField,
                buttonRegular, buttonBorderless,
                check, radio1, radio2,
                ratingBar, seekBar,
                switchButton, toggleButton
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarHolder = ToolbarHolder(this).apply { onCreate() }

        buttonRegular.setOnClickListener { presenter.onDialog(true) }
        buttonBorderless.setOnClickListener { presenter.onDialog(false) }

        disableView.setOnCheckedChangeListener { _, checked -> presenter.onChecked(checked) }
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbarHolder.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        toolbarHolder.setMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = toolbarHolder.onOptionsItemSelected(item)

    override fun onBackPressed() = presenter.back()

    override fun disableUiControls() = toggleUiControls(false)

    override fun enableUiControls() = toggleUiControls(true)

    override fun showLightDialog() = showStyledDialog(R.style.AppTheme_Dialog_Alert_Light)

    override fun showDarkDialog() = showStyledDialog(R.style.AppTheme_Dialog_Alert_Dark)

    private fun toggleUiControls(enabled: Boolean) {
        if (disableView.isChecked != enabled) {
            disableView.isChecked = enabled
        }
        uiControls.forEach { it.isEnabled = enabled }
    }

    private fun showStyledDialog(@StyleRes dialogTheme: Int) = dialogDelegate.showDialog(
            AlertDialog.Builder(this, dialogTheme).run {
                setTitle("Title")
                setMessage("Lots of letters")
                setPositiveButton("OK", { _, _ -> cancelDialog() })
                setNegativeButton("NOK", { _, _ -> cancelDialog() })
                setNeutralButton("Dunno", { _, _ -> cancelDialog() })
                return@run create()
            }
    )

    companion object {
        private const val EXTRA_THEME = "EXTRA_THEME_BOOLEAN_IS_LIGHT"

        fun newIntent(context: Context, @StyleRes styleRes: Int) = Intent(context, PaletteActivity::class.java).apply {
            putExtra(EXTRA_THEME, styleRes)
        }
    }

}
