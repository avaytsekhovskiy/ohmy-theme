package ru.ohmy.theme.presentation.common.navigation

import android.support.annotation.AnimRes

import ru.ohmy.theme.R

@Suppress("MemberVisibilityCanPrivate")
data class AnimationDescriptor(
        @AnimRes val enter: Int,
        @AnimRes val exit: Int,
        @AnimRes val popEnter: Int,
        @AnimRes val popExit: Int
) {

    @Suppress("unused", "MemberVisibilityCanBePrivate")
    companion object {
        fun onlyEnter(@AnimRes enter: Int, @AnimRes exit: Int) = AnimationDescriptor(enter, exit, 0, 0)

        val EMPTY = AnimationDescriptor(0, 0, 0, 0)
        val FADE = onlyEnter(R.anim.fade_in, R.anim.fade_out)
        val FROM_LEFT = AnimationDescriptor(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        val FROM_RIGHT = AnimationDescriptor(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        val FROM_BOTTOM = AnimationDescriptor(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_top, R.anim.exit_to_bottom)
    }

}
