package com.noveogroup.template.presentation.palette

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI
import com.noveogroup.template.presentation.palette.toolbar.ToolbarHolder
import kotlinx.android.synthetic.main.activity_palette.*

@Layout(R.layout.activity_palette)
class PaletteActivity : BaseActivity() {

    @InjectPresenter
    internal lateinit var presenter: PalettePresenter

    @ProvidePresenter
    fun providePresenter(): PalettePresenter = DI.splashScope.getInstance(PalettePresenter::class.java)

    private lateinit var toolbarHolder: ToolbarHolder

    override val themeId get() = intent.getIntExtra(EXTRA_THEME, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarHolder = ToolbarHolder(this).apply { onCreate() }

        switchView.setOnCheckedChangeListener { _, checked -> showToast("Switch: $checked") }
        button.setOnClickListener { dialogDelegate.showDialog(dialog(R.style.AppTheme_Dialog_Alert_Light)) }
        button2.setOnClickListener { dialogDelegate.showDialog(dialog(R.style.AppTheme_Dialog_Alert_Dark)) }
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

    override fun onOptionsItemSelected(item: MenuItem) =
            toolbarHolder.onOptionsItemSelected(item)

    override fun onBackPressed() = presenter.back()

    private fun dialog(@StyleRes dialogTheme: Int) = AlertDialog.Builder(this, dialogTheme).run {
        setTitle("Title")
        setMessage("Lots of letters")
        setPositiveButton("OK", { _, _ -> cancelDialog() })
        setNegativeButton("NOK", { _, _ -> cancelDialog() })
        setNeutralButton("Dunno", { _, _ -> cancelDialog() })
        return@run create()
    }

    companion object {
        private const val EXTRA_THEME = "EXTRA_THEME_BOOLEAN_IS_LIGHT"

        fun newIntent(context: Context, @StyleRes styleRes: Int) = Intent(context, PaletteActivity::class.java).apply {
            putExtra(EXTRA_THEME, styleRes)
        }
    }

}
