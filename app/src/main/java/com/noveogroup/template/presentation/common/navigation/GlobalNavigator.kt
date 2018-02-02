package com.noveogroup.template.presentation.common.navigation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.Fragment
import com.noveogroup.template.domain.navigation.router.GlobalRouter
import com.noveogroup.template.presentation.common.android.BaseActivity
import com.noveogroup.template.presentation.main.MainActivity
import com.noveogroup.template.presentation.splash.SplashActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command

class GlobalNavigator(private val activity: BaseActivity) : SupportAppNavigator(activity, -1) {

    private val mainWorker = AndroidSchedulers.mainThread().createWorker()

    override fun createActivityIntent(screenKey: String, data: Any?): Intent = when (screenKey) {
        GlobalRouter.SPLASH -> SplashActivity.newIntentClearTask(activity)
        GlobalRouter.MAIN -> MainActivity.newIntent(activity)
        GlobalRouter.INSTAGRAM -> openInstagram(data as String)
        else -> throw IllegalArgumentException("Unknown Activity screen key")
    }

    override fun applyCommand(command: Command?) {
        when (command) {
            is Back -> exit()
            is BackTo -> exit()
            else -> super.applyCommand(command)
        }
    }

    override fun exit() {
        mainWorker.schedule { super.exit() }
    }

    override fun createFragment(screenKey: String, data: Any): Fragment {
        throw Error("Global Navigator switches Activities only")
    }

    private fun openInstagram(user: String? = null): Intent {
        val postfix = user?.takeUnless { it.isBlank() }?.let { "_u/$it" } ?: ""
        return Uri.parse("http://instagram.com/$postfix").viewIntent().apply {
            if (isPackageInstalled(PACKAGE_INSTAGRAM_ANDROID)) {
                `package` = PACKAGE_INSTAGRAM_ANDROID
            }
        }
    }

    private fun isPackageInstalled(packageName: String) = try {
        activity.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (ignored: PackageManager.NameNotFoundException) {
        false
    }

    private companion object {
        const val PACKAGE_INSTAGRAM_ANDROID = "com.instagram.android"
    }

    private fun Uri.viewIntent() = Intent(Intent.ACTION_VIEW, this)
}
