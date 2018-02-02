package com.noveogroup.template.core.ext

fun <T> runIf(condition: Boolean, action: () -> T) =
        action.takeIf { condition }?.invoke()


fun <T> runUnless(condition: Boolean, action: () -> T) =
        action.takeUnless { condition }?.invoke()
