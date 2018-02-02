package com.noveogroup.template.presentation.common.navigation

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.noveogroup.template.presentation.common.android.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.android.SupportFragmentNavigator


@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragmentNavigator(
        val activity: BaseActivity,
        val fragmentManager: FragmentManager = activity.supportFragmentManager,
        @IdRes val containerId: Int
) : SupportFragmentNavigator(fragmentManager, containerId) {

    private val mainWorker = AndroidSchedulers.mainThread().createWorker()

    override fun exit() {
        mainWorker.schedule { activity.finish() }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Fragment> SupportFragmentNavigator.findFragmentById(@IdRes idRes: Int) =
            fragmentManager.findFragmentById(idRes) as T?
}
