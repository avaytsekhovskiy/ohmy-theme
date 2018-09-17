package ru.ohmy.theme.presentation.splash

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.ohmy.theme.R
import ru.ohmy.theme.presentation.common.android.BaseActivity
import ru.ohmy.theme.presentation.common.android.inflater.Layout
import ru.ohmy.theme.presentation.di.ActivityScopeInitializer
import ru.ohmy.theme.presentation.di.DI

@Layout(R.layout.activity_splash)
class SplashActivity : BaseActivity() {

    @InjectPresenter
    internal lateinit var presenter: SplashPresenter

    override val lazyScope by lazy { ActivityScopeInitializer { DI.splashScope } }

    @ProvidePresenter
    fun providePresenter() = DI.splashScope.getInstance(SplashPresenter::class.java)!!

    companion object {
        fun newIntentClearTask(context: Context) = Intent(context, SplashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}
