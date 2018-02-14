package com.noveogroup.template.presentation.main.part.toolbar

import android.support.annotation.IdRes
import android.view.Menu
import android.view.MenuItem
import com.noveogroup.template.R

enum class MenuItemDescriptor(@IdRes val id: Int = 0) {
    SETTINGS(R.id.menu_settings),
    SHARE_MAIL(R.id.menu_share_mail),
    SHARE_SMS(R.id.menu_share_sms);

    fun asMenuItem(menu: Menu): MenuItem = menu.findItem(id)

    companion object {
        fun findById(@IdRes id: Int) = values().firstOrNull { it.id == id }
    }
}
