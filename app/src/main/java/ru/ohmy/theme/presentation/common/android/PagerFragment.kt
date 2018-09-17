package ru.ohmy.theme.presentation.common.android


open class PagerFragment : BaseFragment() {

    override fun tryAttachMvp() {
        if (userVisibleHint) {
            super.tryAttachMvp()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log.info("setUserVisibleHint($isVisibleToUser)")
        if (view != null) {
            if (isVisibleToUser) tryAttachMvp()
            else mvpDelegate.onDetach()
        }
    }

}
