package com.noveogroup.template.presentation.common.android.helper


import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

import com.noveogroup.template.R

@Suppress("unused")
class RecyclerClickHelper private constructor(
        private val recyclerView: RecyclerView
) {

    var onItemClickListener: ((Int) -> Unit)? = null
    var onItemLongClickListener: ((Int) -> Boolean)? = null

    private val localAttachListener = makeAttachListener()

    private val localClickListener = View.OnClickListener {
        onItemClickListener?.invoke(recyclerView.getChildViewHolder(it).adapterPosition)
    }

    private val localLongClickListener = View.OnLongClickListener {
        onItemLongClickListener?.invoke(recyclerView.getChildViewHolder(it).adapterPosition) == true
    }

    init {
        this.recyclerView.setTag(R.id.item_click_support, this)
        this.recyclerView.addOnChildAttachStateChangeListener(localAttachListener)
    }

    private fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(localAttachListener)
        view.setTag(R.id.item_click_support, null)
    }

    private fun makeAttachListener() = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View): Unit = with(view) {
            onItemClickListener?.let { setOnClickListener(localClickListener) }
            onItemLongClickListener?.let { setOnLongClickListener(localLongClickListener) }
        }

        override fun onChildViewDetachedFromWindow(view: View) {
            //do nothing
        }
    }

    companion object {
        private fun RecyclerView.clickHelper(@IdRes id: Int) = getTag(id) as RecyclerClickHelper?

        fun from(view: RecyclerView) = view.clickHelper(R.id.item_click_support)
                ?: RecyclerClickHelper(view)

        fun remove(view: RecyclerView) = view.clickHelper(R.id.item_click_support)?.apply { detach(view) }
    }

}
