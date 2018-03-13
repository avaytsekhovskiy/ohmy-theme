package com.noveogroup.template.presentation.palette.page

import com.noveogroup.template.presentation.common.android.BaseFragment


open class PaletteTabFragment : BaseFragment() {

    override fun tryAttachMvp() {
        if (userVisibleHint) {
            super.tryAttachMvp()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        tryAttachMvp()
    }
}
