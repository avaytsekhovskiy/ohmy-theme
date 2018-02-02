package com.noveogroup.template.presentation.splash

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.di.DI

@Layout(R.layout.activity_splash)
class SplashActivity : BaseActivity(), SplashView {

    override val scopeInitializer = SplashScopeInitializer()

    @InjectPresenter
    internal lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = DI.splashScope.getInstance(SplashPresenter::class.java)

    companion object {
        fun newIntentClearTask(context: Context) = Intent(context, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}
