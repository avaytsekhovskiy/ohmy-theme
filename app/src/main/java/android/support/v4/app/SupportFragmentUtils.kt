package android.support.v4.app

import com.noveogroup.template.core.ext.logger

/**
 * Fragment & Loader hacks to fix memory leak.
 * From https://code.google.com/p/android/issues/detail?id=227136
 * FIXME Remove when the bug is fixed in support-v4.
 */
object SupportFragmentUtils {

    private val log by logger()

    /**
     * Hack to force update the LoaderManager's host to avoid a memory leak in retained/detached fragments.
     * Call this in [Fragment.onAttach]
     */
    fun Fragment.updateLoaderManagerHostController() {
        mHost?.getLoaderManager(mWho, mLoadersStarted, false)
    }

    /**
     * This hack is to prevent the root loader manager to leak previous instances of activities
     * accross rotations. It should be called on activities using loaders directly (not via a fragments).
     * If the activity has fragment, you also have to also [.updateLoaderManagerHostController] above
     * for each fragment.
     * Call this in [FragmentActivity.onCreate]
     */
    fun FragmentActivity.updateLoaderManagerHostController() {
        mFragments?.let {
            try {
                val host = it.javaClass.getDeclaredField("mHost").apply { isAccessible = true }.get(it)
                (host as? FragmentHostCallback<*>)?.getLoaderManager("(root)", false, true)
            } catch (e: IllegalAccessException) {
                log.warn("updateLoaderManagerHostController error ${e.javaClass.simpleName}", e)
            } catch (e: NoSuchFieldException) {
                log.warn("updateLoaderManagerHostController error ${e.javaClass.simpleName}", e)
            }
        }
    }

    // workaround bug http://stackoverflow.com/questions/15207305
    fun Fragment.enableChildFragmentManagerAccess() {
        try {
            Fragment::class.java.getDeclaredField("mChildFragmentManager")
                    .apply { isAccessible = true }
                    .apply { set(this@enableChildFragmentManagerAccess, null) }
        } catch (e: NoSuchFieldException) {
            log.warn("enableChildFragmentManagerAccess error {}", e.javaClass.simpleName, e)
        } catch (e: IllegalAccessException) {
            log.warn("enableChildFragmentManagerAccess error {}", e.javaClass.simpleName, e)
        }
    }

    fun FragmentActivity.cleanSupportLibraryIssues() {
        updateLoaderManagerHostController()
        supportFragmentManager.fragments?.forEach { it?.updateLoaderManagerHostController() }
    }

}
