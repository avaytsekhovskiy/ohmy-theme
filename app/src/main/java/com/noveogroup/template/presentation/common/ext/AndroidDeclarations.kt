@file:Suppress("unused")

package com.noveogroup.template.presentation.common.ext

import android.content.Context
import android.os.Build
import android.support.annotation.DimenRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.noveogroup.template.R
import com.noveogroup.template.presentation.common.android.inflater.Layout
import com.noveogroup.template.presentation.common.android.inflater.LayoutWrapper
import java.util.*

/**
 * publish View.visibility to View.VISIBLE
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * publish View.visibility to View.INVISIBLE
 */
fun View.dissolve() {
    visibility = View.INVISIBLE
}

/**
 * publish View.visibility to View.GONE
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * publish View.visibility to View.VISIBLE
 */
fun ViewBinder.showViews(vararg views: View?) = views.forEach { it?.show() }

/**
 * publish View.visibility to View.INVISIBLE
 */
fun ViewBinder.dissolveViews(vararg views: View?) = views.forEach { it?.dissolve() }

/**
 * publish View.visibility to View.GONE
 */
fun ViewBinder.hideViews(vararg views: View?) = views.forEach { it?.hide() }

fun ViewGroup.inflateChild(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View {
    return context.inflater.inflate(layoutId, this, attachToRoot)
}

fun LayoutInflater.contentFrom(source: Any, parent: ViewGroup? = null): View = source.javaClass.run {
    when {
        isAnnotationPresent(Layout::class.java) ->
            inflate(getAnnotation(Layout::class.java).contentId, parent, false)

        isAnnotationPresent(LayoutWrapper::class.java) -> with(getAnnotation(LayoutWrapper::class.java)) {
            inflate(R.layout.wrapper_progress_with_mask, parent, false).apply {
                inflate(contentId, findViewById(R.id.wrapperContentContainer))
                inflate(noContentId, findViewById(R.id.wrapperEmptyContainer))
                inflate(errorId, findViewById(R.id.wrapperErrorContainer))
            }
        }

        else -> throw IllegalArgumentException("Activity or Fragment should use @Layout or @LayoutWrapper annotation")
    }
}

fun Context.dimensionToPx(@DimenRes dimenId: Int) = dpToPx(resources.getDimension(dimenId))

fun Context.dpToPx(dp: Float) = Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

val Context.locale: Locale
    get() = with(resources.configuration) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales.get(0) else locale
    }

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast =
        Toast.makeText(this, message, duration).apply { show() }

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentActivity.findFragmentByContainer(container: ViewGroup) =
        supportFragmentManager.findFragmentById(container.id) as? T

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentActivity.findFragmentById(@IdRes idRes: Int) =
        supportFragmentManager.findFragmentById(idRes) as? T

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Fragment.findFragmentByContainer(container: ViewGroup) =
        childFragmentManager.findFragmentById(container.id) as? T

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Fragment.findFragmentById(@IdRes idRes: Int) =
        childFragmentManager.findFragmentById(idRes) as? T

fun CharSequence.fromHtml(flags: Int? = null): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this.toString(), flags ?: Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this.toString())
    }
}