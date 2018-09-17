package ru.ohmy.theme.presentation.common.android

import android.os.Bundle
import ru.ohmy.theme.core.ext.randomString
import java.util.concurrent.atomic.AtomicReference

interface UniqueIdentifiable {

    companion object {
        const val STATE_SELF_UNIQUE_ID_KEY = "args.activity.unique.key"
    }

    val uniqueIdReference: AtomicReference<String?>

    fun uniqueId(savedInstanceState: Bundle? = null): String = when (savedInstanceState) {
        null -> uniqueIdReference.get()
                ?: randomString(javaClass).also { uniqueIdReference.set(it) }
        else -> savedInstanceState.getString(STATE_SELF_UNIQUE_ID_KEY)!!
    }

    fun saveUniqueId(outState: Bundle?) {
        outState?.putString(STATE_SELF_UNIQUE_ID_KEY, uniqueIdReference.get())
    }

    fun restoreUniqueId(savedInstanceState: Bundle) = with(savedInstanceState) {
        if (containsKey(STATE_SELF_UNIQUE_ID_KEY)) {
            uniqueIdReference.set(getString(STATE_SELF_UNIQUE_ID_KEY))
        }
    }
}
